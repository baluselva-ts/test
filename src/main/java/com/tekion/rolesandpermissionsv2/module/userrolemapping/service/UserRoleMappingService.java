package com.tekion.rolesandpermissionsv2.module.userrolemapping.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.domain.UserRoleMappingDomain;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.entity.UserRoleMappingEntity;

public interface UserRoleMappingService extends BasePostgresService<UserRoleMappingEntity, UserRoleMappingDomain> {

}
