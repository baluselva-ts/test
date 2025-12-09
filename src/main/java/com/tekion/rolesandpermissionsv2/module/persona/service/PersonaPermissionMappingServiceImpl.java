package com.tekion.rolesandpermissionsv2.module.persona.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaPermissionMappingEntity;
import com.tekion.rolesandpermissionsv2.module.persona.repo.PersonaPermissionMappingRepo;
import org.springframework.stereotype.Service;

@Service
public class PersonaPermissionMappingServiceImpl extends BasePostgresServiceImpl<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain, String>
        implements PersonaPermissionMappingService {

    private final PersonaPermissionMappingRepo repository;

    public PersonaPermissionMappingServiceImpl(PersonaPermissionMappingRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
