package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;

public interface ModuleRepo extends BasePostgresRepo<ModuleEntity, ModuleDomain> {

}
