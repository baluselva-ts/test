package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Permission;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.PermissionRecord;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.PermissionEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PermissionRepoImpl extends BasePostgresRepoImpl<PermissionRecord, PermissionEntity, PermissionDomain, String> implements PermissionRepo {

    private final BasePostgresMapper<PermissionEntity, PermissionDomain, String> mapper;
    private final DSLContext dslContext;

    public PermissionRepoImpl(
            BasePostgresMapper<PermissionEntity, PermissionDomain, String> mapper,
            DSLContext dslContext) {
        super(mapper, PermissionEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<PermissionRecord> getTable() {
        return Permission.PERMISSION;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
