package com.tekion.arorapostgres.domain;

import com.tekion.commons.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BasePostgresDomain extends BaseDomain {
}
