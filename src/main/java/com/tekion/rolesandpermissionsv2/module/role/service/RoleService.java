package com.tekion.rolesandpermissionsv2.module.role.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;

public interface RoleService extends BasePostgresService<RoleEntity, RoleDomain> {

}
