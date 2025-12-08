package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.commons.domain.BaseDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import org.jooq.*;

import java.util.List;

@Repository
public class AssetUserPermissionMappingRepoImpl extends BasePostgresRepoImpl<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain> implements AssetUserPermissionMappingRepo {

    private final BasePostgresMapper<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain> mapper;

    public AssetUserPermissionMappingRepoImpl(BasePostgresMapper<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain> mapper) {
        super(mapper, AssetUserPermissionMappingEntity.class);
        this.mapper = mapper;
    }

    @Override
    protected DSLContext getDsl() {
        return null;
    }

}
