package com.tekion.arorapostgres.service;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.commons.request.PageRequest;
import com.tekion.commons.response.PageResponse;
import com.tekion.commons.service.BaseServiceImpl;
import lombok.NonNull;

public class BasePostgresServiceImpl<E extends BasePostgresEntity, D extends BasePostgresDomain> extends BaseServiceImpl<E, D, String> implements BasePostgresService<E, D> {

    private final BasePostgresRepo<E, D> repository;

    public BasePostgresServiceImpl(BasePostgresRepo<E, D> repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PageResponse<D> getAllPaginated(@NonNull PageRequest pageRequest) {
        return repository.getAllPaginated(pageRequest);
    }
}
