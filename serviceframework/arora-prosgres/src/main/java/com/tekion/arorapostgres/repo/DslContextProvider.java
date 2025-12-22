package com.tekion.arorapostgres.repo;

import org.jooq.DSLContext;

public interface DslContextProvider {
    DSLContext getDslContext(String moduleName, String repoLevel, String options);
}
