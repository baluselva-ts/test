package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.AssetUserPermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.AssetUserPermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

@Repository
public class AssetUserPermissionMappingRepoImpl extends BasePostgresRepoImpl<AssetUserPermissionMappingRecord, AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain> implements AssetUserPermissionMappingRepo {

    private final BasePostgresMapper<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain> mapper;
    private final DSLContext dslContext;

    public AssetUserPermissionMappingRepoImpl(
            BasePostgresMapper<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain> mapper,
            DSLContext dslContext) {
        super(mapper, AssetUserPermissionMappingEntity.class);
        this.mapper = mapper;
        this.dslContext = dslContext;
    }

    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }

    @Override
    protected Table<AssetUserPermissionMappingRecord> getTable() {
        return AssetUserPermissionMapping.ASSET_USER_PERMISSION_MAPPING;
    }
}
