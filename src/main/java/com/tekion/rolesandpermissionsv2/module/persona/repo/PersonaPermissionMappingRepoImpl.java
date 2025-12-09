package com.tekion.rolesandpermissionsv2.module.persona.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.AssetUserPermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.PersonaPermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.AssetUserPermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.PersonaPermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaPermissionMappingEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PersonaPermissionMappingRepoImpl extends BasePostgresRepoImpl<PersonaPermissionMappingRecord, PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String> implements PersonaPermissionMappingRepo {

    private final BasePostgresMapper<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String> mapper;
    private final DSLContext dslContext;

    public PersonaPermissionMappingRepoImpl(
            BasePostgresMapper<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String> mapper,
            DSLContext dslContext) {
        super(mapper, PersonaPermissionMappingEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<PersonaPermissionMappingRecord> getTable() {
        return PersonaPermissionMapping.PERSONA_PERMISSION_MAPPING;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
