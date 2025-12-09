package com.tekion.rolesandpermissionsv2.module.role.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.role.domain.RolePermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RolePermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.role.repo.RolePermissionMappingRepo;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionMappingServiceImpl extends BasePostgresServiceImpl<RolePermissionMappingEntity, RolePermissionMappingDomain, String>
        implements RolePermissionMappingService {

    private final RolePermissionMappingRepo repository;

    public RolePermissionMappingServiceImpl(RolePermissionMappingRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
