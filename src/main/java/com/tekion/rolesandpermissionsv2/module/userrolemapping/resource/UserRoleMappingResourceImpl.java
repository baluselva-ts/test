package com.tekion.rolesandpermissionsv2.module.userrolemapping.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;
import com.tekion.rolesandpermissionsv2.module.persona.service.PersonaService;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.domain.UserRoleMappingDomain;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.entity.UserRoleMappingEntity;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.service.UserRoleMappingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-role-mapping")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "UserRoleMapping", description = "UserRoleMapping APIs")
public class UserRoleMappingResourceImpl extends BasePostgresResourceImpl<UserRoleMappingEntity, UserRoleMappingDomain>
        implements UserRoleMappingResource {

    UserRoleMappingService service;

    public UserRoleMappingResourceImpl(UserRoleMappingService service) {
        this.service = service;
    }

    @Override
    protected BaseService<UserRoleMappingEntity, UserRoleMappingDomain, String> getBaseService() {
        return service;
    }
}

