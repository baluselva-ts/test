# JDBC Template Factory - Architecture Diagrams

## 1. Overall Architecture Flow

```
┌─────────────────────────────────────────────────────────────────────┐
│                        Application Layer                             │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  Service Layer (e.g., OutboundService)                       │  │
│  └────────────────────────┬─────────────────────────────────────┘  │
│                           │                                          │
│                           ▼                                          │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  Repository Layer                                            │  │
│  │  ┌────────────────────────────────────────────────────────┐ │  │
│  │  │ DealerLevelOutboundRepositoryImpl                      │ │  │
│  │  │   extends BaseDealerLevelMysqlRepository<Outbound>     │ │  │
│  │  └────────────────────────────────────────────────────────┘ │  │
│  └────────────────────────┬─────────────────────────────────────┘  │
└───────────────────────────┼──────────────────────────────────────────┘
                            │
                            │ getJdbcTemplate()
                            ▼
┌─────────────────────────────────────────────────────────────────────┐
│                    Core Framework Layer                              │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  BaseDealerLevelMysqlRepository                              │  │
│  │  - moduleName: String                                        │  │
│  │  - options: TekMysqlRequestOptions                           │  │
│  │  + getJdbcTemplate(): JdbcTemplate                           │  │
│  └────────────────────────┬─────────────────────────────────────┘  │
│                           │                                          │
│                           ▼                                          │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  DataSourceUtils (Factory)                                   │  │
│  │  + getDataSource(module, tenant, dealer, options)            │  │
│  │  - dataSourceCache: Map<Key, DataSource>                     │  │
│  └────────────────────────┬─────────────────────────────────────┘  │
└───────────────────────────┼──────────────────────────────────────────┘
                            │
                            │ Create/Get DataSource
                            ▼
┌─────────────────────────────────────────────────────────────────────┐
│                    Connection Pool Layer                             │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  HikariCP DataSource                                         │  │
│  │  - jdbcUrl: String                                           │  │
│  │  - username: String                                          │  │
│  │  - password: String                                          │  │
│  │  - minimumIdle: 0                                            │  │
│  │  - maximumPoolSize: 20 (or dealer override)                 │  │
│  │  - scheduledExecutor: hikariScheduledExecutorService         │  │
│  └────────────────────────┬─────────────────────────────────────┘  │
└───────────────────────────┼──────────────────────────────────────────┘
                            │
                            │ Physical Connection
                            ▼
┌─────────────────────────────────────────────────────────────────────┐
│                         Database Layer                               │
│  ┌──────────────────┐  ┌──────────────────┐  ┌─────────────────┐  │
│  │  Primary DB      │  │  Read Replica    │  │  Global DB      │  │
│  │  (Dealer-level)  │  │  (Dealer-level)  │  │  (Tenant-level) │  │
│  └──────────────────┘  └──────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────────────┘
```

## 2. DataSource Creation Flow

```
Repository calls getJdbcTemplate()
           │
           ▼
┌──────────────────────────────────────────────────────────────┐
│ BaseDealerLevelMysqlRepository.getJdbcTemplate()             │
│ - Extract: moduleName, tenantId, dealerId from context       │
└──────────────────────┬───────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────┐
│ DataSourceUtils.getDataSource(module, tenant, dealer, opts)  │
│ - Generate cache key: module + tenant + dealer + type        │
└──────────────────────┬───────────────────────────────────────┘
                       │
                       ▼
              ┌────────┴────────┐
              │                 │
         YES  │  In Cache?      │  NO
              │                 │
              └────┬────────────┴─────┐
                   │                  │
                   ▼                  ▼
        ┌──────────────────┐  ┌──────────────────────────────────┐
        │ Return cached    │  │ Create new HikariDataSource      │
        │ DataSource       │  │ 1. Get DB URL from config        │
        └──────────────────┘  │ 2. Apply default properties      │
                              │ 3. Apply dealer overrides        │
                              │ 4. Set scheduledExecutor         │
                              │ 5. Initialize pool               │
                              │ 6. Cache DataSource              │
                              └──────────┬───────────────────────┘
                                         │
                                         ▼
                              ┌──────────────────────────────────┐
                              │ Return new DataSource            │
                              └──────────┬───────────────────────┘
                                         │
                       ┌─────────────────┴─────────────────┐
                       │                                   │
                       ▼                                   ▼
        ┌──────────────────────────┐      ┌──────────────────────────┐
        │ Create JdbcTemplate      │      │ Wrap in                  │
        │ with DataSource          │      │ NamedParameterJdbcTemplate│
        └──────────┬───────────────┘      └──────────────────────────┘
                   │
                   ▼
        ┌──────────────────────────┐
        │ Return to Repository     │
        └──────────────────────────┘
```

## 3. Multi-Tenancy Routing

```
┌─────────────────────────────────────────────────────────────────┐
│                    Request Context                               │
│  TekionContextProvider.getCurrentTenantId() → "tenant123"       │
│  TekionContextProvider.getCurrentDealerId() → "dealer456"       │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│              Repository Type Determines Scope                    │
│                                                                  │
│  BaseDealerLevelMysqlRepository    BaseGlobalMysqlRepository    │
│         │                                    │                   │
│         ▼                                    ▼                   │
│  Module: ACCOUNTING_DEFAULT         Module: GLOBAL              │
│  Tenant: tenant123                  Tenant: tenant123           │
│  Dealer: dealer456                  Dealer: 0 (global)          │
└────────────────────────┬────────────────────┬───────────────────┘
                         │                    │
                         ▼                    ▼
┌────────────────────────────────┐  ┌────────────────────────────┐
│ DataSource Key:                │  │ DataSource Key:            │
│ ACCOUNTING_DEFAULT_            │  │ GLOBAL_tenant123_0_        │
│ tenant123_dealer456_PRIMARY    │  │ PRIMARY                    │
└────────────────────────────────┘  └────────────────────────────┘
                         │                    │
                         ▼                    ▼
┌────────────────────────────────┐  ┌────────────────────────────┐
│ Database:                      │  │ Database:                  │
│ accounting_tenant123_dealer456 │  │ global_tenant123           │
└────────────────────────────────┘  └────────────────────────────┘
```

## 4. Configuration Override Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                  Default Configuration                           │
│  application.properties:                                         │
│  - mysql.default.min.connections = 0                            │
│  - mysql.default.max.connections = 20                           │
│  - mysql.config.useInformationSchema = true                     │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│              IMySqlConfigPropProvider (Bean)                     │
│  Checks dealer-specific properties:                              │
│  - DpUtils.getSqlPropForConnectionPoolSize()                    │
│  - DpUtils.getSqlForDealerToUsePreparedStatementSetterServerSide()│
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
              ┌──────────┴──────────┐
              │                     │
         YES  │  Override exists?   │  NO
              │                     │
              └──────┬──────────────┴─────┐
                     │                    │
                     ▼                    ▼
        ┌────────────────────┐  ┌────────────────────┐
        │ Use override value │  │ Use default value  │
        │ e.g., maxConn = 50 │  │ e.g., maxConn = 20 │
        └──────────┬─────────┘  └──────────┬─────────┘
                   │                       │
                   └───────────┬───────────┘
                               │
                               ▼
                ┌──────────────────────────────┐
                │ Apply to HikariConfig        │
                │ config.setMaximumPoolSize()  │
                └──────────────────────────────┘
```

## 5. Transaction Management Flow

```
Service calls repository.executeInTransaction()
           │
           ▼
┌──────────────────────────────────────────────────────────────┐
│ BaseDealerLevelMysqlRepository.executeInTransaction()        │
│ - Begin transaction                                          │
└──────────────────────┬───────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────┐
│ Execute TTransactionTask                                     │
│ - repository.save(entity1)                                   │
│ - repository.update(entity2)                                 │
│ - repository.delete(entity3)                                 │
└──────────────────────┬───────────────────────────────────────┘
                       │
                       ▼
              ┌────────┴────────┐
              │                 │
              │  Success?       │
              │                 │
              └────┬────────────┴─────┐
                   │                  │
              YES  │                  │  NO
                   │                  │
                   ▼                  ▼
        ┌──────────────────┐  ┌──────────────────┐
        │ Commit           │  │ Rollback         │
        │ Transaction      │  │ Transaction      │
        └──────┬───────────┘  └──────────────────┘
               │
               ▼
┌──────────────────────────────────────────────────────────────┐
│ Execute After-Commit Hooks (if any)                          │
│ - Send notifications                                         │
│ - Update cache                                               │
│ - Trigger async processes                                    │
└──────────────────────────────────────────────────────────────┘
```

## 6. Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      Spring Context                              │
│                                                                  │
│  ┌────────────────┐  ┌────────────────┐  ┌──────────────────┐  │
│  │ BeanFactory    │  │ Launcher       │  │ DpUtils          │  │
│  │ - hikariSched  │  │ - IMySqlConfig │  │ - getPoolSize()  │  │
│  │   ExecutorSvc  │  │   PropProvider │  │ - getPrepStmt()  │  │
│  └────────┬───────┘  └────────┬───────┘  └────────┬─────────┘  │
│           │                   │                   │             │
└───────────┼───────────────────┼───────────────────┼─────────────┘
            │                   │                   │
            │                   │                   │
            └───────────────────┼───────────────────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │  DataSourceUtils      │
                    │  (Core Library)       │
                    └───────────┬───────────┘
                                │
                                │ Creates & Manages
                                ▼
                    ┌───────────────────────┐
                    │  HikariCP DataSource  │
                    │  Pool                 │
                    └───────────┬───────────┘
                                │
                                │ Provides Connections
                                ▼
                    ┌───────────────────────┐
                    │  JdbcTemplate         │
                    └───────────┬───────────┘
                                │
                                │ Used by
                                ▼
                    ┌───────────────────────┐
                    │  Repository           │
                    │  Implementations      │
                    └───────────────────────┘
```

---

These diagrams illustrate the complete architecture and flow of the JDBC Template Factory pattern used in the Accounting Service.

