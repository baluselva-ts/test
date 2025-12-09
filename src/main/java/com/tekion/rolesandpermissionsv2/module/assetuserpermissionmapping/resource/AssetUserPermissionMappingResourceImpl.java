package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.service.AssetUserPermissionMappingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/asset-user-permission-mapping")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "AssetUserPermissionMapping", description = "AssetUserPermissionMapping APIs")
public class AssetUserPermissionMappingResourceImpl extends BasePostgresResourceImpl<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain>
        implements AssetUserPermissionMappingResource {

    AssetUserPermissionMappingService service;

    public AssetUserPermissionMappingResourceImpl(AssetUserPermissionMappingService service) {
        this.service = service;
    }

    @Override
    protected BaseService<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain, String> getBaseService() {
        return service;
    }
}

