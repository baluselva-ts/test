package com.tekion.arorapostgres.dsl;

import org.jooq.DSLContext;

public interface DslContextProvider {
    DSLContext getDslContext(String moduleName, String repoLevel, String options);
}
