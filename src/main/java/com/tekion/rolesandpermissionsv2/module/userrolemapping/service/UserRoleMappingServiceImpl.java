package com.tekion.rolesandpermissionsv2.module.userrolemapping.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.domain.UserRoleMappingDomain;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.entity.UserRoleMappingEntity;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.repo.UserRoleMappingRepo;
import org.springframework.stereotype.Service;

@Service
public class UserRoleMappingServiceImpl extends BasePostgresServiceImpl<UserRoleMappingEntity, UserRoleMappingDomain>
        implements UserRoleMappingService {

    private final UserRoleMappingRepo repository;

    public UserRoleMappingServiceImpl(UserRoleMappingRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
