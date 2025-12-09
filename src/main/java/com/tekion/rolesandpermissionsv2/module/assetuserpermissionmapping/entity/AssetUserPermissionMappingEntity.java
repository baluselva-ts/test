package com.tekion.rolesandpermissionsv2.module.assetuserpermissionmapping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Data 
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AssetUserPermissionMappingEntity extends BasePostgresEntity<String> {

    private String permissionId;
    private String userId;

    @Override
    public String getAuditAssetType() {
        return "";
    }

}
