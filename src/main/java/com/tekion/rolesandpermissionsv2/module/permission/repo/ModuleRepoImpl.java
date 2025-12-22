package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.arorapostgres.repo.DSLFactory;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Module;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.ModuleRecord;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ModuleRepoImpl extends BasePostgresRepoImpl<ModuleRecord, ModuleEntity, ModuleDomain, String> implements ModuleRepo {

    private final BasePostgresMapper<ModuleEntity, ModuleDomain, String> mapper;

    public ModuleRepoImpl(
            BasePostgresMapper<ModuleEntity, ModuleDomain, String> mapper, DSLFactory dslFactory) {
        super(mapper, ModuleEntity.class, "", dslFactory, null);
        this.mapper = mapper;
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
