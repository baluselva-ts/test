package com.tekion.arorapostgres.repo;


import com.tekion.arorapostgres.config.ClusterFieldConfig;
import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.dsl.DSLFactory;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.transaction.PostgresTransactionManager;
import com.tekion.commons.commons.TekionContextProvider;
import com.tekion.commons.repo.BaseRepoImpl;
import com.tekion.commons.request.PageRequest;
import com.tekion.commons.response.PageResponse;
import lombok.NonNull;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BasePostgresRepoImpl<R extends UpdatableRecord<R>, E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID> extends BaseRepoImpl<E, D, ID>
        implements BasePostgresRepo<E, D, ID> {

    protected final BasePostgresMapper<E, D, ID> mapper;
    protected final Class<E> entityClass;
    private TableField<R, Boolean> isDeletedField;
    private TableField<R, ID> idField;
    private final String moduleName;
    private final DSLFactory dslFactory;
    private final Map<String, String> clusterTypeRepoLevelMap;

    protected BasePostgresRepoImpl(BasePostgresMapper<E, D, ID> mapper, Class<E> entityClass, String moduleName, DSLFactory dslFactory, Map<String, String> clusterTypeRepoLevelMap) {
        super();
        this.mapper = mapper;
        this.entityClass = entityClass;
        this.moduleName = moduleName;
        this.dslFactory = dslFactory;
        this.clusterTypeRepoLevelMap = clusterTypeRepoLevelMap;
    }

    protected DSLContext getDsl() {
        DSLContext threadLocalTx = PostgresTransactionManager.getCurrentTransaction();
        if (threadLocalTx != null) {
            return threadLocalTx;
        }
        return dslFactory.getDslContextForModule(moduleName, clusterTypeRepoLevelMap.get(TekionContextProvider.getClusterType()));
    }

    public DSLContext getDefaultDslContext() {
        return dslFactory.getDslContextForModule(moduleName, clusterTypeRepoLevelMap.get(TekionContextProvider.getClusterType()));
    }

    protected abstract Table<R> getTable();

    protected abstract ID generateId();

    protected TableField<R, ID> getIdField() {
        if (idField == null) {
            this.idField = resolveRequiredField("id");
        }
        return idField;
    }

    protected TableField<R, Boolean> getIsDeletedField() {
        if (isDeletedField == null) {
            this.isDeletedField = resolveRequiredField("is_deleted");
        }
        return isDeletedField;
    }

    private <T> TableField<R, T> resolveRequiredField(String name) {
        Field<?> field = getTable().field(name);
        if (field == null) {
            throw new IllegalStateException(name + " column not found on table " + getTable().getName());
        }
        if (!(field instanceof TableField)) {
            throw new IllegalStateException(name + " is not a TableField on table " + getTable().getName());
        }
        @SuppressWarnings("unchecked")
        TableField<R, T> tableField = (TableField<R, T>) field;
        return tableField;
    }

    protected void setCreateFields(E entity) {
        if (entity.getId() == null) {
            entity.setId(generateId());
        }
        Instant now = Instant.now();

        setClusterFields(entity);

        entity.setIsDeleted(false);
        entity.setCreatedAt(now);
        entity.setCreatedBy(TekionContextProvider.getCurrentUserId());
        entity.setLastUpdatedAt(now);
        entity.setLastUpdatedBy(TekionContextProvider.getCurrentUserId());
        entity.setVersion(1L);
    }

    private void setClusterFields(E entity) {
        String clusterType = TekionContextProvider.getClusterType();
        if (clusterType == null) {
            return;
        }

        String repoLevel = clusterTypeRepoLevelMap.get(clusterType);
        if (repoLevel == null) {
            return;
        }

        List<String> fieldsToSet = ClusterFieldConfig.getFieldsUpToLevel(clusterType, repoLevel);

        for (String fieldName : fieldsToSet) {
            String value = getClusterFieldValue(fieldName);
            if (value != null) {
                setEntityClusterField(entity, fieldName, value);
            }
        }
    }

    private String getClusterFieldValue(String fieldName) {
        switch (fieldName) {
            case "tenantId":
                return TekionContextProvider.getTenantId();
            case "dealerId":
                return TekionContextProvider.getDealerId();
            case "oemId":
                return TekionContextProvider.getOemId();
            case "programId":
                return TekionContextProvider.getProgramId();
            default:
                return null;
        }
    }

    private void setEntityClusterField(E entity, String fieldName, String value) {
        switch (fieldName) {
            case "tenantId":
                entity.setTenantId(value);
                break;
            case "dealerId":
                entity.setDealerId(value);
                break;
            case "oemId":
                entity.setOemId(value);
                break;
            case "programId":
                entity.setProgramId(value);
                break;
            default:
                break;
        }
    }

    protected void setUpdateFields(E entity) {
        entity.setLastUpdatedAt(Instant.now());
        entity.setLastUpdatedBy(TekionContextProvider.getCurrentUserId());
        entity.setVersion(entity.getVersion() + 1);
    }

    @Override
    public D create(@NonNull D domain) {
        E entity = mapper.toFirst(domain);
        setCreateFields(entity);

        DSLContext dsl = getDsl();
        R updatableRecord = dsl.newRecord(getTable());
        updatableRecord.from(entity);
        updatableRecord.insert();

        E persisted = updatableRecord.into(entityClass);
        return mapper.toSecond(persisted);
    }

    @Override
    public List<D> createBulk(@NonNull List<D> domainList) {
        return domainList.stream().map(this::create).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public D getById(@NonNull ID id) {
        DSLContext dsl = getDsl();
        R updatableRecord = dsl.selectFrom(getTable()).where(enrichCondition(getIdField().eq(id))).fetchOne();

        if (updatableRecord == null) {
            return null;
        }

        E entity = updatableRecord.into(entityClass);
        return mapper.toSecond(entity);
    }

    @Override
    public List<D> getByIds(@NonNull List<ID> idList) {
        if (idList.isEmpty()) {
            return List.of();
        }

        DSLContext dsl = getDsl();
        List<E> entities = dsl.selectFrom(getTable()).where(enrichCondition(getIdField().in(idList))).fetch().into(entityClass);

        return mapper.toSecond(entities);
    }

    @Override
    public List<D> getAll() {
        DSLContext dsl = getDsl();
        List<E> entities = dsl.selectFrom(getTable()).where(enrichCondition(null)).fetch().into(entityClass);

        return mapper.toSecond(entities);
    }

    @Override
    public D upsert(@NonNull D domain) {
        E entity = mapper.toFirst(domain);

        if (entity.getId() == null) {
            return create(domain);
        }

        DSLContext dsl = getDsl();
        setUpdateFields(entity);
        R updatableRecord = dsl.newRecord(getTable());
        updatableRecord.from(entity);
        updatableRecord.changed(getIdField(), false);

        R updated = dsl.update(getTable()).set(updatableRecord).where(getIdField().eq(entity.getId())).returning().fetchOne();

        if (updated == null) {
            setCreateFields(entity);
            R inserted = dsl.newRecord(getTable());
            inserted.from(entity);
            inserted.insert();
            E insertedEntity = inserted.into(entityClass);
            return mapper.toSecond(insertedEntity);
        }

        E updatedEntity = updated.into(entityClass);
        return mapper.toSecond(updatedEntity);
    }

    @Override
    public List<D> upsertBulk(@NonNull List<D> domainList) {
        return domainList.stream().map(this::upsert).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public D deleteById(@NonNull ID id) {
        DSLContext dsl = getDsl();
        R deleted = dsl.update(getTable()).set(getIsDeletedField(), Boolean.TRUE).where(getIdField().eq(id)).returning().fetchOne();

        if (deleted == null) {
            return null;
        }

        E entity = deleted.into(entityClass);
        return mapper.toSecond(entity);
    }

    @Override
    public List<D> deleteByIdBulk(@NonNull List<ID> idList) {
        if (idList.isEmpty()) {
            return List.of();
        }

        DSLContext dsl = getDsl();
        List<E> entities = dsl.update(getTable()).set(getIsDeletedField(), Boolean.TRUE).where(getIdField().in(idList)).returning().fetch().into(entityClass);

        return mapper.toSecond(entities);
    }

    @Override
    public D delete(@NonNull D domain) {
        E entity = mapper.toFirst(domain);
        if (entity.getId() == null) {
            return null;
        }
        return deleteById(entity.getId());
    }

    @Override
    public List<D> deleteBulk(@NonNull List<D> domainList) {
        return domainList.stream().map(this::delete).filter(Objects::nonNull).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public PageResponse<D> getAllPaginated(@NonNull PageRequest pageRequest) {
        pageRequest.validate();

        String lastId = decodeCursor(pageRequest.getCursor());

        TableField<R, ?> sortField = null;
        Field<?> field = getTable().field(pageRequest.getSortBy());
        if (field instanceof TableField) {
            sortField = (TableField<R, ?>) field;
        }
        if (sortField == null) {
            sortField = getIdField();
        }

        Condition condition = enrichCondition(null);
        if (lastId != null) {
            @SuppressWarnings("unchecked")
            TableField<R, String> stringField = (TableField<R, String>) sortField;
            if ("DESC".equalsIgnoreCase(pageRequest.getSortDirection())) {
                condition = condition.and(stringField.lt(lastId));
            } else {
                condition = condition.and(stringField.gt(lastId));
            }
        }

        SortField<?> orderBy = "DESC".equalsIgnoreCase(pageRequest.getSortDirection()) ? sortField.desc() : sortField.asc();

        int limit = pageRequest.getPageSize() + 1; // Fetch one extra to detect next page

        List<R> records = getDsl().selectFrom(getTable()).where(condition).orderBy(orderBy).limit(limit).fetch();

        boolean hasNext = records.size() > pageRequest.getPageSize();
        if (hasNext) {
            records = records.subList(0, pageRequest.getPageSize());
        }

	        List<E> entities = records.stream().map(r -> r.into(entityClass)).collect(java.util.stream.Collectors.toList());

        List<D> domains = mapper.toSecond(entities);

	        String nextCursor = null;
	        if (hasNext && !records.isEmpty()) {
	            @SuppressWarnings("unchecked")
	            TableField<R, String> stringField = (TableField<R, String>) sortField;
	            String nextId = records.get(records.size() - 1).get(stringField);
	            nextCursor = encodeCursor(nextId);
	        }

        return PageResponse.<D> builder().data(domains).pageSize(domains.size()).hasNext(hasNext).hasPrevious(lastId != null).nextCursor(nextCursor).previousCursor(pageRequest.getCursor()).build();
    }

    protected Condition enrichCondition(Condition baseCondition) {
        Condition condition = notDeletedCondition().and(clusterCondition(baseCondition));
        if (baseCondition != null) {
            condition = condition.and(baseCondition);
        }
        return condition;
    }

    protected Condition notDeletedCondition() {
        return getIsDeletedField().isNull().or(getIsDeletedField().eq(Boolean.FALSE));
    }

    protected Condition  clusterCondition(Condition baseCondition) {
        String clusterType = TekionContextProvider.getClusterType();
        if (clusterType == null) {
            return DSL.noCondition();
        }

        String repoLevel = clusterTypeRepoLevelMap.get(clusterType);
        if (repoLevel == null) {
            return DSL.noCondition();
        }

        List<String> fieldsForCondition = ClusterFieldConfig.getFieldsUpToLevel(clusterType, repoLevel);
        if (fieldsForCondition.isEmpty()) {
            return DSL.noCondition();
        }

        Condition condition = DSL.noCondition();

        for (String fieldName : fieldsForCondition) {

            String value = getClusterFieldValue(fieldName);
            if (conditionContainsField(baseCondition, fieldName) || value == null || value.isBlank()) {
                continue;
            }

            TableField<R, String> tableField = resolveRequiredField(fieldName);
            condition = condition.and(tableField.eq(value));
        }

        return condition;
    }

    private boolean conditionContainsField(Condition condition, String fieldName) {
        if (condition == null) {
            return false;
        }
        String conditionSql = condition.toString().toLowerCase();
        return conditionSql.contains(fieldName.toLowerCase());
    }

    private String encodeCursor(String id) {
        if (id == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(id.getBytes(StandardCharsets.UTF_8));
    }

    private String decodeCursor(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }
        try {
            return new String(Base64.getDecoder().decode(cursor), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}
