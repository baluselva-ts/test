package com.tekion.rolesandpermissionsv2.module.permission.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;

public interface ModuleService extends BasePostgresService<ModuleEntity, ModuleDomain, String> {

}
