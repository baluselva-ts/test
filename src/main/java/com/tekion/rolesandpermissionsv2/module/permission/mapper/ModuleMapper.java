package com.tekion.rolesandpermissionsv2.module.permission.mapper;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.permission.domain.ModuleDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.ModuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ModuleMapper extends BasePostgresMapper<ModuleEntity, ModuleDomain> {

}
