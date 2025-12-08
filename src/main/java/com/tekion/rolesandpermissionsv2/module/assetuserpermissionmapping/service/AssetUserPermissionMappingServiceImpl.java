package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.service;

import com.bala.sf.domain.supabase.BaseSupabaseServiceImpl;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.repo.AssetUserPermissionMappingRepo;
import org.springframework.stereotype.Service;

@Service
public class AssetUserPermissionMappingServiceImpl extends BaseSupabaseServiceImpl<AccountEntity, AccountDomain> implements AssetUserPermissionMappingService {

    private final AssetUserPermissionMappingRepo repository;

    public AssetUserPermissionMappingServiceImpl(AssetUserPermissionMappingRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
