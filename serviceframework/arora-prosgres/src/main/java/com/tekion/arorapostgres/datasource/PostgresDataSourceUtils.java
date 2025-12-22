package com.tekion.arorapostgres.datasource;

import com.tekion.arorapostgres.repo.ServerConfigFlat;
import com.tekion.arorapostgres.repo.ServerConfigService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Component
public class PostgresDataSourceUtils implements DisposableBean {

    @Value("${postgres.default.connection.timeout:30000}")
    private long defaultConnectionTimeout;

    @Value("${postgres.default.idle.timeout:600000}")
    private long defaultIdleTimeout;

    @Value("${postgres.default.max.lifetime:1800000}")
    private long defaultMaxLifetime;

    private final ServerConfigService serverConfigService;

    @Autowired(required = false)
    private ScheduledExecutorService hikariScheduledExecutorService;

    private final ConcurrentHashMap<CacheKey, HikariDataSource> dataSourceCache = new ConcurrentHashMap<>();

    public PostgresDataSourceUtils(ServerConfigService serverConfigService) {
        this.serverConfigService = serverConfigService;
    }


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

    public DataSource getDataSource(String moduleName, String tenantId, String dealerId,
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

        return dataSourceCache.computeIfAbsent(key, k -> {
            try {
                return createDataSource(moduleName, tenantId, dealerId, oemId, programId, repoLevel, options);
            } catch (Exception e) {
                log.error("Error creating DataSource for key: {}", key, e);
                throw new PostgresDataSourceException(
                    String.format("Failed to create DataSource for module=%s, tenant=%s, dealer=%s, oem=%s, program=%s, level=%s",
                                 moduleName, tenantId, dealerId, oemId, programId, repoLevel), e);
            }
        });
    }

    private HikariDataSource createDataSource(String moduleName, String tenantId, String dealerId,
                                              String oemId, String programId, String repoLevel, String options) {
        log.info("Creating new DataSource for module: {}, tenant: {}, dealer: {}, oem: {}, program: {}, level: {}, options: {}",
                moduleName, tenantId, dealerId, oemId, programId, repoLevel, options);

        ServerConfigFlat serverConfig = serverConfigService.findServerConfig("SQL", moduleName, tenantId, dealerId);

        if (serverConfig == null) {
            throw new PostgresConfigurationException(
                String.format("No server configuration found for module=%s, tenant=%s, dealer=%s",
                    moduleName, tenantId, dealerId));
        }

        HikariConfig config = new HikariConfig();

        applyServerConfiguration(config, serverConfig);

        config.setPoolName(String.format("PostgresPool-%s-%s-%s", moduleName, repoLevel, tenantId));

        if (hikariScheduledExecutorService != null) {
            config.setScheduledExecutor(hikariScheduledExecutorService);
        }

        return new HikariDataSource(config);
    }

    private void applyServerConfiguration(HikariConfig config, ServerConfigFlat serverConfig) {
        if (serverConfig.getJdbcUrl() == null || serverConfig.getJdbcUrl().trim().isEmpty()) {
            throw new PostgresConfigurationException("JDBC URL is required in ServerConfig");
        }

        config.setJdbcUrl(serverConfig.getJdbcUrl());

        if (serverConfig.getUsername() != null) {
            config.setUsername(serverConfig.getUsername());
        }

        if (serverConfig.getPassword() != null) {
            config.setPassword(serverConfig.getPassword());
        }

        int minConnections = serverConfig.getMinConnections() != null ? serverConfig.getMinConnections() : 5;
        int maxConnections = serverConfig.getMaxConnections() != null ? serverConfig.getMaxConnections() : 20;

        if (maxConnections <= 0) {
            throw new PostgresConfigurationException(
                String.format("Max connections must be positive, got: %d", maxConnections));
        }

        if (minConnections < 0) {
            throw new PostgresConfigurationException(
                String.format("Min connections must be non-negative, got: %d", minConnections));
        }

        if (maxConnections < minConnections) {
            throw new PostgresConfigurationException(
                String.format("Max connections (%d) must be >= min connections (%d)",
                             maxConnections, minConnections));
        }

        config.setMinimumIdle(minConnections);
        config.setMaximumPoolSize(maxConnections);
        config.setConnectionTimeout(defaultConnectionTimeout);
        config.setIdleTimeout(defaultIdleTimeout);
        config.setMaxLifetime(defaultMaxLifetime);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
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

        HikariDataSource dataSource = dataSourceCache.remove(key);
        if (dataSource != null) {
            closeDataSource(dataSource, key.toString());
        }
        log.info("Invalidated DataSource cache for key: {}", key);
    }

    public void clearCache() {
        log.info("Clearing all DataSource cache entries");
        dataSourceCache.forEach((key, dataSource) -> closeDataSource(dataSource, key.toString()));
        dataSourceCache.clear();
        log.info("Cleared all DataSource cache entries");
    }

    @Override
    public void destroy() {
        log.info("Shutting down PostgresDataSourceUtils, closing {} DataSource instances", dataSourceCache.size());
        clearCache();
    }

    private void closeDataSource(HikariDataSource dataSource, String key) {
        try {
            if (!dataSource.isClosed()) {
                dataSource.close();
                log.info("Closed HikariDataSource for key: {}", key);
            }
        } catch (Exception e) {
            log.error("Error closing HikariDataSource for key: {}", key, e);
        }
    }

    public CacheStats getCacheStats() {
        return new CacheStats(dataSourceCache.size(), dataSourceCache.keySet());
    }

    @Data
    public static class CacheStats {
        private final int size;
        private final java.util.Set<CacheKey> keys;

        public CacheStats(int size, java.util.Set<CacheKey> keys) {
            this.size = size;
            this.keys = keys;
        }
    }
}

class PostgresDataSourceException extends RuntimeException {
    public PostgresDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}

class PostgresConfigurationException extends RuntimeException {
    public PostgresConfigurationException(String message) {
        super(message);
    }
}

