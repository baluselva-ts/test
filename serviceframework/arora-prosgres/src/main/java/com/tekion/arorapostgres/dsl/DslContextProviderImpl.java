package com.tekion.arorapostgres.dsl;

import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

@Component
public class DslContextProviderImpl implements DslContextProvider {

    private final DSLFactory dslFactory;

    public DslContextProviderImpl(DSLFactory dslFactory) {
        this.dslFactory = dslFactory;
    }

    @Override
    public DSLContext getDslContext(String moduleName, String repoLevel, String options) {
        return dslFactory.getDslContextForModule(moduleName, repoLevel, options);
    }
}
