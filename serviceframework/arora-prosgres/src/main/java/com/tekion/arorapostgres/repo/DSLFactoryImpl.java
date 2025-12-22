package com.tekion.arorapostgres.repo;

import com.tekion.arorapostgres.datasource.PostgresDataSourceUtils;
import com.tekion.commons.commons.TekionContextProvider;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class DSLFactoryImpl implements DSLFactory, ApplicationContextAware, DisposableBean {

    private ApplicationContext applicationContext;
    private final ServerConfigService serverConfigService;
    private final PostgresDataSourceUtils dataSourceUtils;

    private final ConcurrentHashMap<CacheKey, DSLContext> dslContextCache = new ConcurrentHashMap<>();


    @Data
    @Builder
    private static class CacheKey {
        String moduleName;
        String tenantId;
        String dealerId;
        String oemId;
        String programId;
        String repoLevel;
        String options;
    }

    public DSLFactoryImpl(ServerConfigService serverConfigService, PostgresDataSourceUtils dataSourceUtils) {
        this.serverConfigService = serverConfigService;
        this.dataSourceUtils = dataSourceUtils;
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
        String tenantId = TekionContextProvider.getTenantId();
        String dealerId = TekionContextProvider.getDealerId();
        String oemId = TekionContextProvider.getOemId();
        String programId = TekionContextProvider.getProgramId();

        CacheKey key = CacheKey.builder()
                .moduleName(moduleName)
                .tenantId(tenantId)
                .dealerId(dealerId)
                .oemId(oemId)
                .programId(programId)
                .repoLevel(repoLevel)
                .options(options)
                .build();

        return dslContextCache.computeIfAbsent(key, k -> {
            try {
                return createDslContext(moduleName, tenantId, dealerId, oemId, programId, repoLevel, options);
            } catch (Exception e) {
                log.error("Error creating DSLContext for key: {}", key, e);
                throw new DSLContextException(
                    String.format("Failed to create DSLContext for module=%s, tenant=%s, dealer=%s, oem=%s, program=%s, level=%s",
                                 moduleName, tenantId, dealerId, oemId, programId, repoLevel), e);
            }
        });
    }


    private DSLContext createDslContext(String moduleName, String tenantId, String dealerId,
                                       String oemId, String programId, String repoLevel, String options) {
        log.info("Creating new DSLContext for module: {}, tenant: {}, dealer: {}, oem: {}, program: {}, level: {}, options: {}",
                moduleName, tenantId, dealerId, oemId, programId, repoLevel, options);

        DataSource dataSource = dataSourceUtils.getDataSource(
                moduleName, tenantId, dealerId, oemId, programId, repoLevel, options);


        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    public void invalidateCache(String moduleName, String tenantId, String dealerId,
                               String oemId, String programId, String repoLevel, String options) {
        CacheKey key = CacheKey.builder()
                .moduleName(moduleName)
                .tenantId(tenantId)
                .dealerId(dealerId)
                .oemId(oemId)
                .programId(programId)
                .repoLevel(repoLevel)
                .options(options)
                .build();

        dslContextCache.remove(key);
        log.info("Invalidated DSLContext cache for key: {}", key);
    }

    public void clearCache() {
        log.info("Clearing all DSLContext cache entries");
        dslContextCache.clear();
        log.info("Cleared all DSLContext cache entries");
    }

    @Override
    public void destroy() {
        log.info("Shutting down DSLFactoryImpl, clearing {} DSLContext instances", dslContextCache.size());
        clearCache();
    }

    public CacheStats getCacheStats() {
        return new CacheStats(dslContextCache.size(), dslContextCache.keySet());
    }

    @Data
    public static class CacheStats {
        private final int size;
        private final Set<CacheKey> keys;

        public CacheStats(int size, Set<CacheKey> keys) {
            this.size = size;
            this.keys = keys;
        }
    }
}

class DSLContextException extends RuntimeException {
    public DSLContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
