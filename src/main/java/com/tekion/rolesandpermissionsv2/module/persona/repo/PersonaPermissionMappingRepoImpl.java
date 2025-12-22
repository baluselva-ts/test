package com.tekion.rolesandpermissionsv2.module.persona.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.arorapostgres.dsl.DSLFactory;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.PersonaPermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.PersonaPermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaPermissionMappingEntity;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PersonaPermissionMappingRepoImpl extends BasePostgresRepoImpl<PersonaPermissionMappingRecord, PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String>
        implements PersonaPermissionMappingRepo {

    private final BasePostgresMapper<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String> mapper;

    public PersonaPermissionMappingRepoImpl(
            BasePostgresMapper<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String> mapper, DSLFactory dslFactory) {
        super(mapper, PersonaPermissionMappingEntity.class, "", dslFactory, null);
        this.mapper = mapper;
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
