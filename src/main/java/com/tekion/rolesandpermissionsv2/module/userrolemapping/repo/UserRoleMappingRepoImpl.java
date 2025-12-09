package com.tekion.rolesandpermissionsv2.module.userrolemapping.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.UserRoleMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.UserRoleMappingRecord;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.domain.UserRoleMappingDomain;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.entity.UserRoleMappingEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRoleMappingRepoImpl extends BasePostgresRepoImpl<UserRoleMappingRecord, UserRoleMappingEntity, UserRoleMappingDomain, String> implements UserRoleMappingRepo {

    private final BasePostgresMapper<UserRoleMappingEntity, UserRoleMappingDomain, String> mapper;
    private final DSLContext dslContext;

    public UserRoleMappingRepoImpl(
            BasePostgresMapper<UserRoleMappingEntity, UserRoleMappingDomain, String> mapper,
            DSLContext dslContext) {
        super(mapper, UserRoleMappingEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<UserRoleMappingRecord> getTable() {
        return UserRoleMapping.USER_ROLE_MAPPING;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
