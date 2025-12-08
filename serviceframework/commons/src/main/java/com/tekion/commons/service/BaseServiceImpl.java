package com.tekion.commons.service;


import com.tekion.commons.domain.BaseDomain;
import com.tekion.commons.entity.BaseEntity;
import com.tekion.commons.repo.BaseRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<E extends BaseEntity<ID>, D extends BaseDomain, ID>
	        implements BaseService<E, D, ID> {

	    private final BaseRepo<E, D, ID> repository;

    @Override
    public D create(@NonNull D domain) {
        return repository.create(domain);
    }

    @Override
    public List<D> createBulk(@NonNull List<D> domainList) {
        return repository.createBulk(domainList);
    }

    @Override
    public D getById(@NonNull ID id) {
        return repository.getById(id);
    }

    @Override
    public List<D> getByIds(@NonNull List<ID> ids) {
        return repository.getByIds(ids);
    }

    @Override
    public List<D> getAll() {
        return repository.getAll();
    }

    @Override
    public D upsert(@NonNull D domain) {
        return repository.upsert(domain);
    }

    @Override
    public List<D> upsertBulk(@NonNull List<D> domainList) {
        return repository.upsertBulk(domainList);
    }

    @Override
    public D deleteById(@NonNull ID id) {
        return repository.deleteById(id);
    }

    @Override
    public List<D> deleteByIdBulk(@NonNull List<ID> ids) {
        return repository.deleteByIdBulk(ids);
    }

    @Override
    public D delete(@NonNull D domain) {
        return repository.delete(domain);
    }

    @Override
    public List<D> deleteBulk(@NonNull List<D> domainList) {
        return repository.deleteBulk(domainList);
    }
}
