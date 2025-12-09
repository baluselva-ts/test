package com.tekion.rolesandpermissionsv2.module.persona.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.Persona;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.PersonaRecord;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepoImpl extends BasePostgresRepoImpl<PersonaRecord, PersonaEntity, PersonaDomain> implements PersonaRepo {

    private final BasePostgresMapper<PersonaEntity, PersonaDomain> mapper;
    private final DSLContext dslContext;

    public PersonaRepoImpl(
            BasePostgresMapper<PersonaEntity, PersonaDomain> mapper,
            DSLContext dslContext) {
        super(mapper, PersonaEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<PersonaRecord> getTable() {
        return Persona.PERSONA;
    }
}
