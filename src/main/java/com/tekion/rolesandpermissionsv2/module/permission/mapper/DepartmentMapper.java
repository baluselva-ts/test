package com.tekion.rolesandpermissionsv2.module.permission.mapper;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.permission.domain.DepartmentDomain;
import com.tekion.rolesandpermissionsv2.module.permission.entity.DepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DepartmentMapper extends BasePostgresMapper<DepartmentEntity, DepartmentDomain> {

}
