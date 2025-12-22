package com.tekion.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServerConfigServiceImpl implements ServerConfigService {

    @Value("${spring.datasource.url}")
    private String defaultJdbcUrl;

    @Value("${postgres.default.username:#{null}}")
    private String defaultUsername;

    @Value("${postgres.default.password:#{null}}")
    private String defaultPassword;

    @Value("${postgres.default.max.connections:20}")
    private Integer defaultMaxConnections;

    @Value("${postgres.default.min.connections:5}")
    private Integer defaultMinConnections;

    @Override
    public ServerConfigFlat findServerConfig(String serverType, String moduleName, String tenantId, String dealerId) {
        log.debug("Finding server config for serverType: {}, module: {}, tenant: {}, dealer: {}",
                serverType, moduleName, tenantId, dealerId);

        return ServerConfigFlat.builder()
                .serverType(serverType)
                .moduleName(moduleName)
                .tenantId(tenantId)
                .dealerId(dealerId)
                .jdbcUrl(buildJdbcUrl(moduleName, tenantId, dealerId))
                .username(defaultUsername)
                .password(defaultPassword)
                .maxConnections(defaultMaxConnections)
                .minConnections(defaultMinConnections)
                .isReadReplica(false)
                .build();
    }

    private String buildJdbcUrl(String moduleName, String tenantId, String dealerId) {
        return defaultJdbcUrl;
    }
}

