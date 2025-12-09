package com.tekion.rolesandpermissionsv2.module.permission.mapper;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.permission.domain.PermissionDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PermissionMapper extends BasePostgresMapper<PermissionEntity, PermissionDomain> {

}
