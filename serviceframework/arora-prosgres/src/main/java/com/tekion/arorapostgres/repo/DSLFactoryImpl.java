package com.tekion.arorapostgres.repo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tekion.commons.commons.TekionContextProvider;
import org.jooq.DSLContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Duration;

@Service
public class DSLFactoryImpl implements DSLFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final ServerConfigService serverConfigService;
    private final Cache<String, DSLContext> dslContextCache;

    public DSLFactoryImpl(ServerConfigService serverConfigService) {
        this.serverConfigService = serverConfigService;
        this.dslContextCache = createCache();
    }

    private Cache<String, DSLContext> createCache() {
        return CacheBuilder.newBuilder().expireAfterAccess(Duration.ofHours(24)).build();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public DSLContext getDslContextForModule(String moduleName, String repoLevel) {
        return doGetDslContext(moduleName, repoLevel, null);
    }

    @Override
    public DSLContext getDslContextForModule(String moduleName, String repoLevel, String options) {
        return doGetDslContext(moduleName, repoLevel, options);
    }

    private DSLContext doGetDslContext(String moduleName, String repoLevel, String options) {
        DSLContext dslContext = null;
        String cacheKey = String.format("%s_%s_%s_%s_%s", moduleName, TekionContextProvider.getTenantId(), TekionContextProvider.getDealerId(), TekionContextProvider.getOemId(), TekionContextProvider.getProgramId());
        ServerConfigFlat config =
                serverConfigService.findServerConfig(
                        "SQL", moduleName, TekionContextProvider.getTenantId(), TekionContextProvider.getDealerId());
        return dslContext;
    }

    private DSLContext getGlobalDslContext(String moduleName, String options) {
        return null;
    }

    private DSLContext getTenantDslContext(String moduleName, String options) {
        String cacheKey = TekionContextProvider.getTenantId();
        return null;
    }

    private DSLContext getDealerDslContext(String moduleName, String options) {
        String cacheKey = TekionContextProvider.getTenantId() + "_" + TekionContextProvider.getDealerId();
        return null;
    }

    private DSLContext getOemDslContext(String moduleName, String options) {

        return null;
    }

    private DSLContext getProgramDslContext(String moduleName, String options) {
        return null;
    }

}
