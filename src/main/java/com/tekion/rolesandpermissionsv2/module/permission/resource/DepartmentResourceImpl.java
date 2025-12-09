package com.tekion.rolesandpermissionsv2.module.permission.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.service.AssetUserPermissionMappingService;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;
import com.tekion.rolesandpermissionsv2.module.permission.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/permission-department")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission Department", description = "Permission Department APIs")
public class DepartmentResourceImpl extends BasePostgresResourceImpl<DepartmentEntity, DepartmentDomain, String>
        implements DepartmentResource {

    DepartmentService service;

    public DepartmentResourceImpl(DepartmentService service) {
        this.service = service;
    }

    @Override
    protected BaseService<DepartmentEntity, DepartmentDomain, String> getBaseService() {
        return service;
    }
}

