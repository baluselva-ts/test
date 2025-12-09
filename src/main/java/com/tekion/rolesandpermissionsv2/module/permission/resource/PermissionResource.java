package com.tekion.rolesandpermissionsv2.module.permission.resource;

import com.tekion.arorapostgres.resource.BasePostgresResource;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.PermissionEntity;

public interface PermissionResource extends BasePostgresResource<PermissionEntity, PermissionDomain> {

}

