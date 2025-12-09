package com.tekion.rolesandpermissionsv2.module.role.mapper;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.role.domain.RoleDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RoleMapper extends BasePostgresMapper<RoleEntity, RoleDomain, String> {

}
