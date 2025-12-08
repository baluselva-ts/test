package com.tekion.arorapostgres.domain;

import com.tekion.commons.domain.BaseDomain;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BasePostgresDomain extends BaseDomain {
}
