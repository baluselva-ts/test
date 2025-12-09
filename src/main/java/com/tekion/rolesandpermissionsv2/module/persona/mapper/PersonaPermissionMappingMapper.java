package com.tekion.rolesandpermissionsv2.module.persona.mapper;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaPermissionMappingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PersonaPermissionMappingMapper extends BasePostgresMapper<PersonaPermissionMappingEntity, PersonaPermissionMappingDomain> {

}
