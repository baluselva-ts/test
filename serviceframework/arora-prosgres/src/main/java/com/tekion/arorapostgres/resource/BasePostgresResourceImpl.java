package com.tekion.arorapostgres.resource;


import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.commons.resource.BaseResourceImpl;

public abstract class BasePostgresResourceImpl<E extends BasePostgresEntity, D extends BasePostgresDomain>
        extends BaseResourceImpl<E, D, String>
        implements BasePostgresResource<E, D> {

}
