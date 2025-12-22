package com.tekion.arorapostgres.dsl;

import org.jooq.DSLContext;

public interface DSLFactory {
    DSLContext getDslContextForModule(String moduleName, String repoLevel, String options);
    DSLContext getDslContextForModule(String moduleName, String repoLevel);
}
