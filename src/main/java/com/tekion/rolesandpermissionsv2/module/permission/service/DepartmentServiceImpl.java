package com.tekion.rolesandpermissionsv2.module.permission.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.repo.AssetUserPermissionMappingRepo;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;
import com.tekion.rolesandpermissionsv2.module.permission.repo.DepartmentRepo;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends BasePostgresServiceImpl<DepartmentEntity, DepartmentDomain, String>
        implements DepartmentService {

    private final DepartmentRepo repository;

    public DepartmentServiceImpl(DepartmentRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
