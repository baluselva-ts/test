package com.tekion.rolesandpermissionsv2.module.role.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.arorapostgres.dsl.DSLFactory;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.RolePermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.RolePermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.module.role.domain.RolePermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RolePermissionMappingEntity;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RolePermissionMappingRepoImpl extends BasePostgresRepoImpl<RolePermissionMappingRecord, RolePermissionMappingEntity, RolePermissionMappingDomain, String>
        implements RolePermissionMappingRepo {

    private final BasePostgresMapper<RolePermissionMappingEntity, RolePermissionMappingDomain, String> mapper;

    public RolePermissionMappingRepoImpl(
            BasePostgresMapper<RolePermissionMappingEntity, RolePermissionMappingDomain, String> mapper, DSLFactory dslFactory) {
        super(mapper, RolePermissionMappingEntity.class, "", dslFactory, null);
        this.mapper = mapper;
    }

    @Override
    protected Table<RolePermissionMappingRecord> getTable() {
        return RolePermissionMapping.ROLE_PERMISSION_MAPPING;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
