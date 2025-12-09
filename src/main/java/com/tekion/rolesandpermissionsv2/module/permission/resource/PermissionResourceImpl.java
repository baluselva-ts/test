package com.tekion.rolesandpermissionsv2.module.permission.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.service.AssetUserPermissionMappingService;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.PermissionEntity;
import com.tekion.rolesandpermissionsv2.module.permission.service.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/permission")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission", description = "Permission APIs")
public class PermissionResourceImpl extends BasePostgresResourceImpl<PermissionEntity, PermissionDomain>
        implements PermissionResource {

    PermissionService service;

    public PermissionResourceImpl(PermissionService service) {
        this.service = service;
    }

    @Override
    protected BaseService<PermissionEntity, PermissionDomain, String> getBaseService() {
        return service;
    }
}

