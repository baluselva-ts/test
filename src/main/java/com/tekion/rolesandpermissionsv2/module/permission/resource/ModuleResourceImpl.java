package com.tekion.rolesandpermissionsv2.module.permission.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.service.AssetUserPermissionMappingService;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;
import com.tekion.rolesandpermissionsv2.module.permission.service.ModuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/permission-module")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission Module", description = "Permission Module APIs")
public class ModuleResourceImpl extends BasePostgresResourceImpl<ModuleEntity, ModuleDomain, String>
        implements ModuleResource {

    ModuleService service;

    public ModuleResourceImpl(ModuleService service) {
        this.service = service;
    }

    @Override
    protected BaseService<ModuleEntity, ModuleDomain, String> getBaseService() {
        return service;
    }
}

