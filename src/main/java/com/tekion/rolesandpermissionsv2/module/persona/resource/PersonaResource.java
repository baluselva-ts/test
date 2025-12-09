package com.tekion.rolesandpermissionsv2.module.persona.resource;

import com.tekion.arorapostgres.resource.BasePostgresResource;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;

public interface PersonaResource extends BasePostgresResource<PersonaEntity, PersonaDomain> {

}

