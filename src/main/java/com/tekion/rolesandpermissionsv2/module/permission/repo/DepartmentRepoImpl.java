package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.arorapostgres.repo.DSLFactory;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Department;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.DepartmentRecord;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DepartmentRepoImpl extends BasePostgresRepoImpl<DepartmentRecord, DepartmentEntity, DepartmentDomain, String> implements DepartmentRepo {

    private final BasePostgresMapper<DepartmentEntity, DepartmentDomain, String> mapper;

    public DepartmentRepoImpl(
            BasePostgresMapper<DepartmentEntity, DepartmentDomain, String> mapper, DSLFactory dslFactory) {
        super(mapper, DepartmentEntity.class, "", dslFactory, null);
        this.mapper = mapper;
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
