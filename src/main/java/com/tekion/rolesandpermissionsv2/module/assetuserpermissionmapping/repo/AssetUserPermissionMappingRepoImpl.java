package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.repo;

import com.tekion.arorapostgres.mapper.BasePostgresMapper;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import com.tekion.arorapostgres.dsl.DSLFactory;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.AssetUserPermissionMapping;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.AssetUserPermissionMappingRecord;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.domain.AssetUserPermissionMappingDomain;
import com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity.AssetUserPermissionMappingEntity;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class AssetUserPermissionMappingRepoImpl extends BasePostgresRepoImpl<AssetUserPermissionMappingRecord, AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain, String>
        implements AssetUserPermissionMappingRepo {

    private final BasePostgresMapper<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain, String> mapper;
    private final DSLFactory dslFactory;

    public AssetUserPermissionMappingRepoImpl(
            BasePostgresMapper<AssetUserPermissionMappingEntity, AssetUserPermissionMappingDomain, String> mapper, DSLFactory dslFactory) {
        super(mapper, AssetUserPermissionMappingEntity.class, "", dslFactory, Map.of("ARC", "tenant", "OEM", "oem"));
        this.mapper = mapper;
        this.dslFactory = dslFactory;
    }

    @Override
    protected Table<AssetUserPermissionMappingRecord> getTable() {
        return AssetUserPermissionMapping.ASSET_USER_PERMISSION_MAPPING;
    }

    @Override
    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
