package com.tekion.rolesandpermissionsv2.module.role.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;
import com.tekion.rolesandpermissionsv2.module.role.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/role")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Role", description = "Role APIs")
public class RoleResourceImpl extends BasePostgresResourceImpl<RoleEntity, RoleDomain, String>
        implements RoleResource {

    RoleService service;

    public RoleResourceImpl(RoleService service) {
        this.service = service;
    }

    @Override
    protected BaseService<RoleEntity, RoleDomain, String> getBaseService() {
        return service;
    }
}

