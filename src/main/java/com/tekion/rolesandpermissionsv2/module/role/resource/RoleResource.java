package com.tekion.rolesandpermissionsv2.module.role.resource;

import com.tekion.arorapostgres.resource.BasePostgresResource;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;

public interface RoleResource extends BasePostgresResource<RoleEntity, RoleDomain, String> {

}

