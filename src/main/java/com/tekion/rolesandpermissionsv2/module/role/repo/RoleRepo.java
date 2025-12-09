package com.tekion.rolesandpermissionsv2.module.role.repo;

import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;

public interface RoleRepo extends BasePostgresRepo<RoleEntity, RoleDomain> {

}
