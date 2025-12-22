package com.tekion.rolesandpermissionsv2.module.persona.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.arorapostgres.repo.DSLFactory;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Persona;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.PersonaRecord;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PersonaRepoImpl extends BasePostgresRepoImpl<PersonaRecord, PersonaEntity, PersonaDomain, String> implements PersonaRepo {

    private final BasePostgresMapper<PersonaEntity, PersonaDomain, String> mapper;

    public PersonaRepoImpl(
            BasePostgresMapper<PersonaEntity, PersonaDomain, String> mapper, DSLFactory dslFactory) {
        super(mapper, PersonaEntity.class, "", dslFactory, null);
        this.mapper = mapper;
    }

    @Override
    protected Table<PersonaRecord> getTable() {
        return Persona.PERSONA;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
