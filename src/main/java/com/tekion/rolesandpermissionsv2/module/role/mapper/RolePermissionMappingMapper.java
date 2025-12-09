package com.tekion.rolesandpermissionsv2.module.role.mapper;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.role.domain.RolePermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.role.entity.RolePermissionMappingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RolePermissionMappingMapper extends BasePostgresMapper<RolePermissionMappingEntity, RolePermissionMappingDomain> {

}
