package com.tekion.rolesandpermissionsv2.module.role.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;
import com.tekion.rolesandpermissionsv2.module.role.repo.RoleRepo;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BasePostgresServiceImpl<RoleEntity, RoleDomain>
        implements RoleService {

    private final RoleRepo repository;

    public RoleServiceImpl(RoleRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
