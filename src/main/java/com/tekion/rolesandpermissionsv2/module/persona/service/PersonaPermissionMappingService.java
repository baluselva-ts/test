package com.tekion.rolesandpermissionsv2.module.persona.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaPermissionMappingEntity;

public interface PersonaPermissionMappingService extends BasePostgresService<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain> {

}
