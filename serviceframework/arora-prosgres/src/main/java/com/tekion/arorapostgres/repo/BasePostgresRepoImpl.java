package com.tekion.arorapostgres.repo;


import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.arorapostgres.mapper.BasePostgresMapper;
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
import java.util.Objects;

public abstract class BasePostgresRepoImpl<R extends UpdatableRecord<R>, E extends BasePostgresEntity, D extends BasePostgresDomain> extends BaseRepoImpl<E, D, String>
        implements BasePostgresRepo<E, D> {

    protected final BasePostgresMapper<E, D> mapper;
    protected final Class<E> entityClass;
    private TableField<R, Boolean> isDeletedField;
    private TableField<R, String> idField;
    private TableField<R, String> scopeIdField;

    protected BasePostgresRepoImpl(BasePostgresMapper<E, D> mapper, Class<E> entityClass) {
        super();
        this.mapper = mapper;
        this.entityClass = entityClass;
    }

    protected abstract DSLContext getDsl();

    protected abstract Table<R> getTable();

    protected TableField<R, String> getIdField() {
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

    protected TableField<R, String> getScopeIdField() {
        if (scopeIdField == null) {
            this.scopeIdField = resolveRequiredField("scope_id");
        }
        return scopeIdField;
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
            entity.setId(java.util.UUID.randomUUID().toString());
        }
        Instant now = Instant.now();
        entity.setScopeId(TekionContextProvider.getCurrentScopeId());
        entity.setIsDeleted(false);
        entity.setCreatedAt(now);
        entity.setCreatedBy(TekionContextProvider.getCurrentUserId());
        entity.setLastUpdatedAt(now);
        entity.setLastUpdatedBy(TekionContextProvider.getCurrentUserId());
        entity.setVersion(1L);
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

        R updatableRecord = getDsl().newRecord(getTable());
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
    public D getById(@NonNull String id) {
        R updatableRecord = getDsl().selectFrom(getTable()).where(enrichCondition(getIdField().eq(id))).fetchOne();

        if (updatableRecord == null) {
            return null;
        }

        E entity = updatableRecord.into(entityClass);
        return mapper.toSecond(entity);
    }

    @Override
    public List<D> getByIds(@NonNull List<String> idList) {
        if (idList.isEmpty()) {
            return List.of();
        }

        List<E> entities = getDsl().selectFrom(getTable()).where(enrichCondition(getIdField().in(idList))).fetch().into(entityClass);

        return mapper.toSecond(entities);
    }

    @Override
    public List<D> getAll() {
        List<E> entities = getDsl().selectFrom(getTable()).where(enrichCondition(null)).fetch().into(entityClass);

        return mapper.toSecond(entities);
    }

    @Override
    public D upsert(@NonNull D domain) {
        E entity = mapper.toFirst(domain);

        if (entity.getId() == null) {
            return create(domain);
        }

        setUpdateFields(entity);
        R updatableRecord = getDsl().newRecord(getTable());
        updatableRecord.from(entity);
        updatableRecord.changed(getIdField(), false);

        R updated = getDsl().update(getTable()).set(updatableRecord).where(getIdField().eq(entity.getId())).returning().fetchOne();

        if (updated == null) {
            setCreateFields(entity);
            R inserted = getDsl().newRecord(getTable());
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
    public D deleteById(@NonNull String id) {
        R deleted = getDsl().update(getTable()).set(getIsDeletedField(), Boolean.TRUE).where(getIdField().eq(id)).returning().fetchOne();

        if (deleted == null) {
            return null;
        }

        E entity = deleted.into(entityClass);
        return mapper.toSecond(entity);
    }

    @Override
    public List<D> deleteByIdBulk(@NonNull List<String> idList) {
        if (idList.isEmpty()) {
            return List.of();
        }

        List<E> entities = getDsl().update(getTable()).set(getIsDeletedField(), Boolean.TRUE).where(getIdField().in(idList)).returning().fetch().into(entityClass);

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

        TableField<R, String> sortField = null;
        Field<?> field = getTable().field(pageRequest.getSortBy());
        if (field instanceof TableField) {
            @SuppressWarnings("unchecked")
            TableField<R, String> tempField = (TableField<R, String>) field;
            sortField = tempField;
        }
        if (sortField == null) {
            sortField = getIdField();
        }

        Condition condition = enrichCondition(null);
        if (lastId != null) {
            if ("DESC".equalsIgnoreCase(pageRequest.getSortDirection())) {
                condition = condition.and(sortField.lt(lastId));
            } else {
                condition = condition.and(sortField.gt(lastId));
            }
        }

        SortField<String> orderBy = "DESC".equalsIgnoreCase(pageRequest.getSortDirection()) ? sortField.desc() : sortField.asc();

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
	            String nextId = records.get(records.size() - 1).get(sortField);
	            nextCursor = encodeCursor(nextId);
	        }

        return PageResponse.<D> builder().data(domains).pageSize(domains.size()).hasNext(hasNext).hasPrevious(lastId != null).nextCursor(nextCursor).previousCursor(pageRequest.getCursor()).build();
    }

    protected Condition enrichCondition(Condition baseCondition) {
        Condition condition = notDeletedCondition().and(scopeIdCondition());
        if (baseCondition != null) {
            condition = condition.and(baseCondition);
        }
        return condition;
    }

    protected Condition notDeletedCondition() {
        return getIsDeletedField().isNull().or(getIsDeletedField().eq(Boolean.FALSE));
    }

    protected Condition scopeIdCondition() {
        String currentScopeId = TekionContextProvider.getCurrentScopeId();
        if (currentScopeId == null || currentScopeId.isBlank()) {
            return DSL.noCondition();
        }
        return getScopeIdField().eq(currentScopeId);
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
