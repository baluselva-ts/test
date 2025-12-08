package com.tekion.arorapostgres.mapper;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.commons.mapper.BaseMapper;

public interface BasePostgresMapper<E extends BasePostgresEntity, D extends BasePostgresDomain>
        extends BaseMapper<E, D> {

    default String toString(Long id) {
        return String.valueOf(id);
    }

    default Long toLong(String id) {
        return Long.parseLong(id);
    }
}
