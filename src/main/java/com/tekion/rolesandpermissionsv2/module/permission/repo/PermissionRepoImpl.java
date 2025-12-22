package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.arorapostgres.dsl.DSLFactory;
import com.tekion.rolesandpermissionsv2.config.RepoClusterConfig;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Permission;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.PermissionRecord;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.PermissionEntity;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PermissionRepoImpl extends BasePostgresRepoImpl<PermissionRecord, PermissionEntity, PermissionDomain, String> implements PermissionRepo {

    private final BasePostgresMapper<PermissionEntity, PermissionDomain, String> mapper;

    public PermissionRepoImpl(
            BasePostgresMapper<PermissionEntity, PermissionDomain, String> mapper, DSLFactory dslFactory) {
        super(mapper, PermissionEntity.class, "", dslFactory, RepoClusterConfig.getPermissionClusterMap());
        this.mapper = mapper;
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
