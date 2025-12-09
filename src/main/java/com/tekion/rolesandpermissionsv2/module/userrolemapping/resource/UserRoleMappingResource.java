package com.tekion.rolesandpermissionsv2.module.userrolemapping.resource;

import com.tekion.arorapostgres.resource.BasePostgresResource;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.domain.UserRoleMappingDomain;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.entity.UserRoleMappingEntity;

public interface UserRoleMappingResource extends BasePostgresResource<UserRoleMappingEntity, UserRoleMappingDomain> {

}

