package com.tekion.arorapostgres.resource;


import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.commons.resource.BaseResourceImpl;

public abstract class BasePostgresResourceImpl<E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID>
        extends BaseResourceImpl<E, D, ID>
        implements BasePostgresResource<E, D, ID> {

}
