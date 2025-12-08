package com.tekion.commons.resource;

import com.tekion.commons.domain.BaseDomain;
import com.tekion.commons.entity.BaseEntity;
import lombok.NonNull;

import java.util.List;

public interface BaseResource<E extends BaseEntity<ID>, D extends BaseDomain, ID> {

    D create(@NonNull D domain);

    List<D> createBulk(@NonNull List<D> domainList);

    D getById(@NonNull ID id);

    List<D> getByIds(@NonNull List<ID> idList);

    List<D> getAll();

    D upsert(@NonNull D domain);

    List<D> upsertBulk(@NonNull List<D> domainList);

    D deleteById(@NonNull ID id);

    List<D> deleteByIdBulk(@NonNull List<ID> idList);

    D delete(@NonNull D domain);

    List<D> deleteBulk(@NonNull List<D> domainList);

}
