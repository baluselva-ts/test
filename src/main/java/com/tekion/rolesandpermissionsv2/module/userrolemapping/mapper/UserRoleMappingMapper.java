package com.tekion.rolesandpermissionsv2.module.userrolemapping.mapper;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.persona.domain.PersonaDomain;
import com.tekion.rolesandpermissionsv2.module.persona.entity.PersonaEntity;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.domain.UserRoleMappingDomain;
import com.tekion.rolesandpermissionsv2.module.userrolemapping.entity.UserRoleMappingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserRoleMappingMapper extends BasePostgresMapper<UserRoleMappingEntity, UserRoleMappingDomain> {

}
