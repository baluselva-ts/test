package com.tekion.rolesandpermissionsv2.module.role.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Role;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.RoleRecord;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RoleRepoImpl extends BasePostgresRepoImpl<RoleRecord, RoleEntity, RoleDomain, String> implements RoleRepo {

    private final BasePostgresMapper<RoleEntity, RoleDomain, String> mapper;
    private final DSLContext dslContext;

    public RoleRepoImpl(
            BasePostgresMapper<RoleEntity, RoleDomain, String> mapper,
            DSLContext dslContext) {
        super(mapper, RoleEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<RoleRecord> getTable() {
        return Role.ROLE;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
