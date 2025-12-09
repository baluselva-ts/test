package com.tekion.rolesandpermissionsv2.module.permission.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;
import com.tekion.rolesandpermissionsv2.module.permission.repo.ModuleRepo;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl extends BasePostgresServiceImpl<ModuleEntity, ModuleDomain, String>
        implements ModuleService {

    private final ModuleRepo repository;

    public ModuleServiceImpl(ModuleRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
