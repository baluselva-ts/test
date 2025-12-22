package com.tekion.arorapostgres.repo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerConfigFlat {

    private String serverType;
    private String moduleName;
    private String tenantId;
    private String dealerId;
    private String oemId;
    private String programId;
    private String jdbcUrl;
    private String username;
    private String password;
    private String host;
    private Integer port;
    private String databaseName;
    private String schemaName;
    private Boolean isReadReplica;
    private Integer maxConnections;
    private Integer minConnections;
}
