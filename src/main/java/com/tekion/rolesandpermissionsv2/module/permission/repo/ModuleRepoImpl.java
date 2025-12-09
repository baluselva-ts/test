package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Module;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.ModuleRecord;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ModuleRepoImpl extends BasePostgresRepoImpl<ModuleRecord, ModuleEntity, ModuleDomain, String> implements ModuleRepo {

    private final BasePostgresMapper<ModuleEntity, ModuleDomain, String> mapper;
    private final DSLContext dslContext;

    public ModuleRepoImpl(
            BasePostgresMapper<ModuleEntity, ModuleDomain, String> mapper,
            DSLContext dslContext) {
        super(mapper, ModuleEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<ModuleRecord> getTable() {
        return Module.MODULE;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
