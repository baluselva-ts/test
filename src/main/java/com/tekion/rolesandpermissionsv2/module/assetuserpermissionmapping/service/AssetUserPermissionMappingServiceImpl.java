package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.repo.AssetUserPermissionMappingRepo;
import org.springframework.stereotype.Service;

@Service
public class AssetUserPermissionMappingServiceImpl extends BasePostgresServiceImpl<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain, String>
        implements AssetUserPermissionMappingService {

    private final AssetUserPermissionMappingRepo repository;

    public AssetUserPermissionMappingServiceImpl(AssetUserPermissionMappingRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
