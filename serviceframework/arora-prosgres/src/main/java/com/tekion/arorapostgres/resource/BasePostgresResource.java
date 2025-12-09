package com.tekion.arorapostgres.resource;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.commons.resource.BaseResource;
import com.tekion.commons.response.PageResponse;

public interface BasePostgresResource<E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID> extends BaseResource<E, D, ID> {

}
