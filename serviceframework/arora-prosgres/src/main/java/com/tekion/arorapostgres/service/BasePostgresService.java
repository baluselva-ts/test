package com.tekion.arorapostgres.service;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.commons.request.PageRequest;
import com.tekion.commons.response.PageResponse;
import com.tekion.commons.service.BaseService;
import lombok.NonNull;

public interface BasePostgresService<E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID>
        extends BaseService<E, D, ID> {

    PageResponse<D> getAllPaginated(@NonNull PageRequest pageRequest);
}
