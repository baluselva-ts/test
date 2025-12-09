package com.tekion.rolesandpermissionsv2.module.permission.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.PermissionEntity;

public interface PermissionService extends BasePostgresService<PermissionEntity, PermissionDomain, String> {

}
