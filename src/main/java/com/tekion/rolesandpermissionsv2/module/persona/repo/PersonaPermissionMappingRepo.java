package com.tekion.rolesandpermissionsv2.module.persona.repo;

import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaPermissionMappingEntity;

public interface PersonaPermissionMappingRepo extends BasePostgresRepo<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String> {

}
