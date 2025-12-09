package com.tekion.arorapostgres.repo;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.commons.repo.BaseRepo;
import com.tekion.commons.request.PageRequest;
import com.tekion.commons.response.PageResponse;
import lombok.NonNull;

public interface BasePostgresRepo<E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID>
        extends BaseRepo<E, D, ID> {

    PageResponse<D> getAllPaginated(@NonNull PageRequest pageRequest);

}
