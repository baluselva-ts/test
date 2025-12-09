package com.tekion.rolesandpermissionsv2.module.persona.repo;

import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;

public interface PersonaRepo extends BasePostgresRepo<PersonaEntity, PersonaDomain, String> {

}
