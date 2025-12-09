package com.tekion.rolesandpermissionsv2.module.permission.resource;

import com.tekion.arorapostgres.resource.BasePostgresResource;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;

public interface DepartmentResource extends BasePostgresResource<DepartmentEntity, DepartmentDomain, String> {

}

