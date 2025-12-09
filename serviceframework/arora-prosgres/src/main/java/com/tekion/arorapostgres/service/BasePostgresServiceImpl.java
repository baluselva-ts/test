package com.tekion.arorapostgres.service;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.commons.request.PageRequest;
import com.tekion.commons.response.PageResponse;
import com.tekion.commons.service.BaseServiceImpl;
import lombok.NonNull;

public class BasePostgresServiceImpl<E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID> extends BaseServiceImpl<E, D, ID> implements BasePostgresService<E, D, ID> {

    private final BasePostgresRepo<E, D, ID> repository;

    public BasePostgresServiceImpl(BasePostgresRepo<E, D, ID> repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PageResponse<D> getAllPaginated(@NonNull PageRequest pageRequest) {
        return repository.getAllPaginated(pageRequest);
    }
}
