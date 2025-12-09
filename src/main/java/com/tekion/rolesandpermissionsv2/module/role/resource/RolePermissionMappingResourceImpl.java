package com.tekion.rolesandpermissionsv2.module.role.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.role.domain.RolePermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RolePermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.role.service.RolePermissionMappingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/role-permission-mapping")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "RolePermissionMapping", description = "RolePermissionMapping APIs")
public class RolePermissionMappingResourceImpl extends BasePostgresResourceImpl<RolePermissionMappingEntity, RolePermissionMappingDomain, String>
        implements RolePermissionMappingResource {

    RolePermissionMappingService service;

    public RolePermissionMappingResourceImpl(RolePermissionMappingService service) {
        this.service = service;
    }

    @Override
    protected BaseService<RolePermissionMappingEntity, RolePermissionMappingDomain, String> getBaseService() {
        return service;
    }
}

