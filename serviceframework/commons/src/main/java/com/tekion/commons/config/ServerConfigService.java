package com.tekion.commons.config;

public interface ServerConfigService {

    ServerConfigFlat findServerConfig(String serverType, String moduleName, String tenantId, String dealerId);
}

