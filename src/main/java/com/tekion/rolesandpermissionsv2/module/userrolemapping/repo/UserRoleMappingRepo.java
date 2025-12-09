package com.tekion.rolesandpermissionsv2.module.userrolemapping.repo;

import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.domain.UserRoleMappingDomain;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.entity.UserRoleMappingEntity;

public interface UserRoleMappingRepo extends BasePostgresRepo<UserRoleMappingEntity, UserRoleMappingDomain, String> {

}
