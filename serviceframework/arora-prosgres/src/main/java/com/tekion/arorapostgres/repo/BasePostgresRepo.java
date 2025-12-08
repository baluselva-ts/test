package com.tekion.arorapostgres.repo;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.commons.repo.BaseRepo;
import com.tekion.commons.request.PageRequest;
import com.tekion.commons.response.PageResponse;
import lombok.NonNull;

public interface BasePostgresRepo<E extends BasePostgresEntity, D extends BasePostgresDomain>
        extends BaseRepo<E, D, Long> {

    PageResponse<D> getAllPaginated(@NonNull PageRequest pageRequest);

}
