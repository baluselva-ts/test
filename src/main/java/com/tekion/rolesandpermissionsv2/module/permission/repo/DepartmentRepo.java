package com.tekion.rolesandpermissionsv2.module.permission.repo;

import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;

public interface DepartmentRepo extends BasePostgresRepo<DepartmentEntity, DepartmentDomain> {

}
