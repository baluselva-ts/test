package com.tekion.commons.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@NoArgsConstructor
public abstract class BaseEntity<ID> {

    ID id;
    String name;
    String label;
    String scopeId;
    String tenantId;
    String dealerId;
    String oemId;
    String programId;
    @Builder.Default
    Boolean isDeleted = false;
    @Builder.Default
    Boolean isArchived = false;
    @Builder.Default
    Boolean isCustom = false;
    @Builder.Default
    Boolean isReadOnly = false;
    @Builder.Default
    Boolean isInternal = false;
    Long version;
    Instant createdAt;
    String createdBy;
    Instant lastUpdatedAt;
    String lastUpdatedBy;

    public abstract String getAuditAssetType();

    public String getAuditAssetId() {
        return scopeId + "_" + id;
    }
    public String getAuditParentAssetType(){
        return null;
    }
    public String getAuditParentAssetId(){
        return null;
    }

}
