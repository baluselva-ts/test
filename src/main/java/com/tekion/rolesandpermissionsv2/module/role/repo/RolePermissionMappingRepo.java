package com.tekion.rolesandpermissionsv2.module.role.repo;

import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.rolesandpermissionsv2.module.role.domain.RolePermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RolePermissionMappingEntity;

public interface RolePermissionMappingRepo extends BasePostgresRepo<RolePermissionMappingEntity, RolePermissionMappingDomain, String> {

}
