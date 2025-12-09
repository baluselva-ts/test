package com.tekion.rolesandpermissionsv2.module.persona.service;

import com.tekion.arorapostgres.service.BasePostgresServiceImpl;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;
import com.tekion.rolesandpermissionsv2.module.permission.repo.ModuleRepo;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;
import com.tekion.rolesandpermissionsv2.module.persona.repo.PersonaRepo;
import org.springframework.stereotype.Service;

@Service
public class PersonaServiceImpl extends BasePostgresServiceImpl<PersonaEntity, PersonaDomain>
        implements PersonaService {

    private final PersonaRepo repository;

    public PersonaServiceImpl(PersonaRepo repository) {
        super(repository);
        this.repository = repository;
    }

}
