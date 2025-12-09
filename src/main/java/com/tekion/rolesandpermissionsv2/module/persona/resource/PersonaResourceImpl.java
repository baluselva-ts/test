package com.tekion.rolesandpermissionsv2.module.persona.resource;

import com.tekion.arorapostgres.resource.BasePostgresResourceImpl;
import com.tekion.commons.service.BaseService;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;
import com.tekion.rolesandpermissionsv2.module.persona.service.PersonaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/persona")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Persona", description = "Persona APIs")
public class PersonaResourceImpl extends BasePostgresResourceImpl<PersonaEntity, PersonaDomain, String>
        implements PersonaResource {

    PersonaService service;

    public PersonaResourceImpl(PersonaService service) {
        this.service = service;
    }

    @Override
    protected BaseService<PersonaEntity, PersonaDomain, String> getBaseService() {
        return service;
    }
}

