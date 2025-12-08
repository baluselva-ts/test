package com.tekion.rolesandpermissionsv2.module.permission.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionDomain extends BasePostgresDomain {

    private String moduleId;
    private String departmentId;

}
