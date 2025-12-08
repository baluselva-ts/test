package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.mapper;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AssetUserPermissionMapper extends BasePostgresMapper<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain> {

}
