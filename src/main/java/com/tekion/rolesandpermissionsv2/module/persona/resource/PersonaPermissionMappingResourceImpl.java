package com.tekion.rolesandpermissionsv2.module.persona.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.persona.service.PersonaPermissionMappingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/persona-permission-mapping")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Persona Permission Mapping", description = "Persona Permission Mapping APIs")
public class PersonaPermissionMappingResourceImpl extends BasePostgresResourceImpl<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String>
        implements PersonaPermissionMappingResource {

    PersonaPermissionMappingService service;

    public PersonaPermissionMappingResourceImpl(PersonaPermissionMappingService service) {
        this.service = service;
    }

    @Override
    protected BaseService<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String> getBaseService() {
        return service;
    }
}

