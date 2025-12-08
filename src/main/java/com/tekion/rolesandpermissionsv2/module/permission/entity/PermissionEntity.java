package com.tekion.rolesandpermissionsv2.module.permission.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionEntity extends BasePostgresEntity {

    private String moduleId;
    private String departmentId;

    @Override
    public String getAuditAssetType() {
        return "";
    }

}
