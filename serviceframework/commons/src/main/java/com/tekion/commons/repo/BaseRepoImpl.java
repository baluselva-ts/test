package com.tekion.commons.repo;

import com.tekion.commons.domain.BaseDomain;
import com.tekion.commons.entity.BaseEntity;

public abstract class BaseRepoImpl<E extends BaseEntity<ID>, D extends BaseDomain, ID>
        implements BaseRepo<E, D, ID> {
}
