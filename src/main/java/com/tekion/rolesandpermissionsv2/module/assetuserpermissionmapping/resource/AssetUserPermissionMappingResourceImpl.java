package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.resource;

import com.bala.sf.domain.base.BaseService;
import com.bala.sf.domain.supabase.BaseSupabaseResourceImpl;
import com.bala.sf.domain.supabase.BaseSupabaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Account", description = "Account management APIs")
public class AssetUserPermissionMappingResourceImpl extends BaseSupabaseResourceImpl<AccountEntity, AccountDomain>
        implements AssetUserPermissionMappingResource {

    AccountService service;

    public AssetUserPermissionMappingResourceImpl(AccountService service) {
        this.service = service;
    }
    

    @Override
    protected BaseService<AccountEntity, AccountDomain, Long> getBaseService() {
        return service;
    }

    @Override
    protected BaseSupabaseService<AccountEntity, AccountDomain> getSupabaseService() {
        return service;
    }
}

