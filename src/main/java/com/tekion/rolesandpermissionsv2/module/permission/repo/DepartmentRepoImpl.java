package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.AssetUserPermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Department;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.AssetUserPermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.DepartmentRecord;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DepartmentRepoImpl extends BasePostgresRepoImpl<DepartmentRecord, DepartmentEntity, DepartmentDomain, String> implements DepartmentRepo {

    private final BasePostgresMapper<DepartmentEntity, DepartmentDomain, String> mapper;
    private final DSLContext dslContext;

    public DepartmentRepoImpl(
            BasePostgresMapper<DepartmentEntity, DepartmentDomain, String> mapper,
            DSLContext dslContext) {
        super(mapper, DepartmentEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<DepartmentRecord> getTable() {
        return Department.DEPARTMENT;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
