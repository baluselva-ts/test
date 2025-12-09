package com.tekion.rolesandpermissionsv2.module.permission.service;

import com.tekion.arorapostgres.service.BasePostgresService;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;

public interface DepartmentService extends BasePostgresService<DepartmentEntity, DepartmentDomain> {

}
