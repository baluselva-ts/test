package com.tekion.rolesandpermissionsv2.module.role.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.RolePermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.RolePermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.module.role.domain.RolePermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RolePermissionMappingEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionMappingRepoImpl extends BasePostgresRepoImpl<RolePermissionMappingRecord, RolePermissionMappingEntity, RolePermissionMappingDomain> implements RolePermissionMappingRepo {

    private final BasePostgresMapper<RolePermissionMappingEntity, RolePermissionMappingDomain> mapper;
    private final DSLContext dslContext;

    public RolePermissionMappingRepoImpl(
            BasePostgresMapper<RolePermissionMappingEntity, RolePermissionMappingDomain> mapper,
            DSLContext dslContext) {
        super(mapper, RolePermissionMappingEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<RolePermissionMappingRecord> getTable() {
        return RolePermissionMapping.ROLE_PERMISSION_MAPPING;
    }
}
