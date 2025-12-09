package com.tekion.rolesandpermissionsv2.module.permission.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.PermissionEntity;
import com.tekion.rolesandpermissionsv2.module.permission.repo.PermissionRepo;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends BasePostgresServiceImpl<PermissionEntity, PermissionDomain>
        implements PermissionService {

    private final PermissionRepo repository;

    public PermissionServiceImpl(PermissionRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
