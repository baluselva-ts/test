package com.tekion.arorapostgres.repo;

public interface ServerConfigService {

    ServerConfigFlat findServerConfig(String serverType, String moduleName, String tenantId, String dealerId);
}
