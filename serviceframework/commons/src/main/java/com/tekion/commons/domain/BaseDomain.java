package com.tekion.commons.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
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
	    String latUpdatedBy;

}
