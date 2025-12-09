package com.tekion.rolesandpermissionsv2.module.persona.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;

public interface PersonaService extends BasePostgresService<PersonaEntity, PersonaDomain> {

}
