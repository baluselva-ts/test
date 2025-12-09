package com.tekion.rolesandpermissionsv2.module.role.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.role.domain.RolePermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RolePermissionMappingEntity;

public interface RolePermissionMappingService extends BasePostgresService<RolePermissionMappingEntity, RolePermissionMappingDomain> {

}
