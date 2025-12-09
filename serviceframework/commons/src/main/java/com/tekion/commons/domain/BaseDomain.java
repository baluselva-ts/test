package com.tekion.commons.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseDomain {

	    String id;
	    String name;
	    String label;
	    String scopeId;
	    Boolean isDeleted;
	    Boolean isArchived;
	    Boolean isCustom;
	    Boolean isReadOnly;
	    Boolean isInternal;
	    Long version;
	    Instant createdAt;
	    String createdBy;
	    Instant lastUpdatedAt;
	    String lastUpdatedBy;

}
