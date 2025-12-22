# JDBC Template Factory Pattern - Comprehensive Context Documentation

## Overview
This document provides a comprehensive understanding of how JdbcTemplate instances are created and managed in the Accounting Service, including datasource creation, connection pooling, and the repository pattern implementation.

## Architecture Pattern

### 1. Base Repository Pattern
The system uses an inheritance-based repository pattern with two main base classes:

#### BaseDealerLevelMysqlRepository<T>
- **Purpose**: Manages dealer-specific database connections and operations
- **Module Name**: Typically initialized with `ACCOUNTING_DEFAULT` or `ModuleName.ACCOUNTING_DEFAULT.name()`
- **Scope**: Dealer-level data isolation
- **Constructor**: `super(moduleName)` or `super(moduleName, TekMysqlRequestOptions)`

#### BaseGlobalMysqlRepository<T>
- **Purpose**: Manages global/tenant-level database connections
- **Module Name**: Typically initialized with `KEYWORD_CONFIG_GLOBAL_ASSET_TYPE`
- **Scope**: Cross-dealer, tenant-level or global data
- **Constructor**: `super(moduleName)`

### 2. JdbcTemplate Access Pattern
Both base repository classes provide access to JdbcTemplate through:
```java
protected JdbcTemplate getJdbcTemplate()
```

This method is inherited from the base classes and provides:
- **Lazy initialization**: JdbcTemplate is created on-demand
- **Connection pooling**: Backed by HikariCP datasource
- **Multi-tenancy support**: Automatically routes to correct database based on context
- **Read replica support**: Can be configured with `TekMysqlRequestOptions.READER_DEFAULT`

## DataSource Creation and Management

### 1. HikariCP Connection Pool Configuration

#### Default Configuration (from application.properties)
```properties
mysql.config.useInformationSchema=true
mysql.query.disableColumnMetadata=true
mysql.default.min.connections=0
mysql.default.max.connections=20  # Service: 20, Migration: 32
```

#### Dynamic Configuration Override
The system supports dynamic property-based configuration through `IMySqlConfigPropProvider`:

```java
@Bean
@Primary
public IMySqlConfigPropProvider doGetIMySqlConfigPropProvider() {
    return fetchRequest -> {
        // Dealer-specific overrides
        int sqlPropForConnectionPoolSize = DpUtils.getSqlPropForConnectionPoolSize();
        int sqlPreparedStatementServerSide = DpUtils.getSqlForDealerToUsePreparedStatementSetterServerSide();

        SqlPropertyResponseDto sqlPropertyResponseDto = new SqlPropertyResponseDto();

        if (sqlPropForConnectionPoolSize != -1) {
            sqlPropertyResponseDto.setMaxConnections(SqlOverride.getEnabledOverride(sqlPropForConnectionPoolSize));
        }

        if (sqlPreparedStatementServerSide == 0) {
            sqlPropertyResponseDto.setUserServerPreparedStatement(SqlOverride.getEnabledOverride(false));
        } else if (sqlPreparedStatementServerSide == 1) {
            sqlPropertyResponseDto.setUserServerPreparedStatement(SqlOverride.getEnabledOverride(true));
        }

        return sqlPropertyResponseDto;
    };
}
```

### 2. HikariCP Scheduled Executor Service
Custom executor service for HikariCP housekeeping tasks:

```java
@Bean
public ScheduledExecutorService hikariScheduledExecutorService() {
    boolean isProduction = TEnvConstants.CLUSTER_TYPE.contains("prod");
    final ThreadFactory threadFactory = new UtilityElf.DefaultThreadFactory("Hikari housekeeper", true);
    final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
        isProduction ? 100 : 20,  // Pool size based on environment
        threadFactory,
        new ThreadPoolExecutor.DiscardPolicy()
    );
    executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
    executor.setRemoveOnCancelPolicy(true);
    return executor;
}
```

**Purpose**:
- Connection leak detection
- Connection validation
- Pool maintenance
- Idle connection eviction

### 3. DataSourceUtils (Core Library)
Referenced in: `com.tekion.core.mysql.DataSourceUtils`

**Key Responsibilities**:
- Creates and manages HikariCP DataSource instances
- Handles multi-tenant database routing
- Manages connection pool lifecycle
- Provides datasource caching per module/tenant/dealer combination
- Integrates with the custom HikariScheduledExecutorService

## Repository Implementation Pattern

### Example 1: Dealer-Level Repository
```java
@Component
public class DealerLevelOutboundRepositoryImpl
    extends BaseDealerLevelMysqlRepository<Outbound>
    implements OutboundRepository {

    private final OutboundRepoHelper outboundRepoHelper;

    public DealerLevelOutboundRepositoryImpl(OutboundRepoHelper outboundRepoHelper) {
        super(ACCOUNTING_DEFAULT);  // Module name
        this.outboundRepoHelper = outboundRepoHelper;
    }

    @Override
    public Outbound save(Outbound outbound) {
        // getJdbcTemplate() provides the configured JdbcTemplate
        return outboundRepoHelper.save(outbound, getJdbcTemplate());
    }

    @Override
    public void insertBulk(List<Outbound> outbounds) {
        // Direct access to mysqlDao with JdbcTemplate
        this.mysqlDao.insertBatch(getJdbcTemplate(), outbounds);
    }
}
```

### Example 2: Global-Level Repository
```java
@Component
public class GlobalLevelOutboundRepositoryImpl
    extends BaseGlobalMysqlRepository<Outbound>
    implements OutboundRepository {

    public GlobalLevelOutboundRepositoryImpl(OutboundRepoHelper outboundRepoHelper) {
        super(KEYWORD_CONFIG_GLOBAL_ASSET_TYPE);  // Global module
        this.outboundRepoHelper = outboundRepoHelper;
    }

    @Override
    public Outbound save(Outbound outbound) {
        return outboundRepoHelper.save(outbound, getJdbcTemplate());
    }
}
```

### Example 3: Read Replica Repository
```java
@Component
public class TransactionReadReplicaRepositoryImpl
    extends BaseDealerLevelMysqlRepository<Transaction>
    implements TransactionReadReplicaRepository {

    public TransactionReadReplicaRepositoryImpl() {
        // TekMysqlRequestOptions.READER_DEFAULT routes to read replica
        super(ACCOUNTING_DEFAULT, TekMysqlRequestOptions.READER_DEFAULT);
    }
}
```

## Common Database Operations

### 1. Insert Operations
```java
// Single insert
this.mysqlDao.insert(getJdbcTemplate(), entity);

// Batch insert
this.mysqlDao.insertBatch(getJdbcTemplate(), entityList);
```

### 2. Query Operations
```java
// Query with criteria
Criteria criteria = new Criteria(DEALER_ID).is(dealerId);
Query query = Query.fromCriteria(criteria);
List<Entity> results = this.mysqlDao.queryList(getJdbcTemplate(), query, Entity.class);

// Query single object
Entity entity = this.mysqlDao.queryObject(getJdbcTemplate(), query, Entity.class);
```

### 3. Update Operations
```java
// Update with criteria
Criteria criteria = new Criteria(ID).is(id);
Update update = new Update().set(STATUS, newStatus);
this.mysqlDao.update(getJdbcTemplate(), Query.fromCriteria(criteria), update, Entity.class);
```

### 4. Named Parameter Queries
```java
// For complex SQL queries
NamedParameterJdbcTemplate namedTemplate =
    new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());

Map<String, Object> paramMap = new HashMap<>();
paramMap.put("dealerId", dealerId);
paramMap.put("status", status);

List<Map<String, Object>> results = namedTemplate.queryForList(sqlQuery, paramMap);
```

### 5. Batch Operations with PreparedStatement
```java
getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        Entity entity = entities.get(i);
        ps.setString(1, entity.getId());
        ps.setString(2, entity.getName());
        // ... set other parameters
    }

    @Override
    public int getBatchSize() {
        return entities.size();
    }
});
```

## Transaction Management

### 1. Programmatic Transactions
```java
@Override
public <K, T> K executeInTransaction(TTransactionTask<K> task, TTransactionTask<T>... afterCommits) {
    return super.executeInTransaction(task, afterCommits);
}
```

**Usage**:
```java
repository.executeInTransaction(() -> {
    // All operations here are in a single transaction
    repository.save(entity1);
    repository.update(entity2);
    return result;
});
```

### 2. After-Commit Hooks
```java
repository.executeInTransaction(
    () -> {
        // Main transaction logic
        return mainResult;
    },
    () -> {
        // Executed after successful commit
        sendNotification();
        return null;
    }
);
```

## Multi-Tenancy and Context Management

### 1. Tenant/Dealer Context
The framework automatically handles multi-tenancy through:
- `TekionContextProvider.getCurrentTenantId()`
- `TekionContextProvider.getCurrentDealerId()`

### 2. Database Routing
- **Dealer-level repositories**: Route to dealer-specific database schema
- **Global repositories**: Route to global/tenant database schema
- **Read replicas**: Route to read-only database instances

### 3. Context Propagation
```java
// Context is automatically propagated to:
// - JdbcTemplate operations
// - Async operations (via DelegatingTekionContextExecutorServiceToAsyncTaskWrapper)
// - Transaction boundaries
```



## Performance Optimization Patterns

### 1. Index Management
```java
@Autowired
private IndexManager indexManager;

String indexName = indexManager.getIndexName(
    (MysqlDaoImpl) mysqlDao,
    getJdbcTemplate(),
    Entity.class,
    Arrays.asList("column1", "column2")
);

String sqlQuery = "SELECT * FROM table FORCE INDEX(" + indexName + ") WHERE ...";
```

### 2. Batch Processing
```java
BatchIterator<Entity> iterator = new BatchIterator<>(
    new BatchDataRetriever<Entity>() {
        @Override
        public List<Entity> fetch(int offset, int rows, Entity lastObject) {
            // Fetch batch of data
            return fetchBatch(offset, rows);
        }
    },
    batchSize
);

while (iterator.hasNext()) {
    List<Entity> batch = iterator.next();
    processBatch(batch);
}
```

### 3. Connection Pool Sizing
```java
// Dynamic per-dealer configuration
public static int getSqlPropForConnectionPoolSize() {
    Integer dealerVal = dpProvider.getValForDp(
        RegisteredDp.ACCOUNTING_SQL_PROP_CONNECTION_POOL_SIZE.getDpName(),
        Integer.class,
        -1
    );
    return dealerVal;
}
```

## Key Configuration Constants

### Module Names
- `ACCOUNTING_DEFAULT`: Standard accounting module
- `KEYWORD_CONFIG_GLOBAL_ASSET_TYPE`: Global configuration module
- `ModuleName.ACCOUNTING_DEFAULT.name()`: Enum-based module name

### Request Options
- `TekMysqlRequestOptions.READER_DEFAULT`: Routes to read replica
- Default (no option): Routes to primary database

## Spring Boot Auto-Configuration Exclusions

The application explicitly excludes Spring's default datasource auto-configuration:

```java
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
```

**Reason**: Custom multi-tenant datasource management through DataSourceUtils

## Best Practices

### 1. Repository Design
- Extend `BaseDealerLevelMysqlRepository` for dealer-scoped data
- Extend `BaseGlobalMysqlRepository` for global/tenant-scoped data
- Use helper classes for complex query logic
- Keep repository methods focused on data access

### 2. JdbcTemplate Usage
- Always use `getJdbcTemplate()` from base class
- Never create JdbcTemplate instances manually
- Leverage `NamedParameterJdbcTemplate` for complex queries
- Use batch operations for bulk inserts/updates

### 3. Connection Pool Management
- Configure pool size based on workload
- Use read replicas for read-heavy operations
- Monitor connection usage through HikariCP metrics
- Set appropriate timeout values

### 4. Transaction Boundaries
- Keep transactions short and focused
- Use `executeInTransaction` for multi-step operations
- Avoid long-running operations in transactions
- Use after-commit hooks for non-critical operations

## Common Patterns Summary

### Creating a New Repository
1. Extend appropriate base class (`BaseDealerLevelMysqlRepository` or `BaseGlobalMysqlRepository`)
2. Call `super(moduleName)` in constructor
3. Inject helper services if needed
4. Use `getJdbcTemplate()` for all database operations
5. Leverage `mysqlDao` for standard CRUD operations

### Executing Queries
1. Build criteria using `Criteria` and `Query` classes
2. Use `mysqlDao.queryList()` or `mysqlDao.queryObject()`
3. For complex SQL, use `NamedParameterJdbcTemplate`
4. Always handle null/empty results

### Managing Transactions
1. Use `executeInTransaction()` for transactional operations
2. Group related operations in single transaction
3. Use after-commit hooks for async/notification tasks
4. Handle exceptions appropriately

## DataSourceUtils Deep Dive

### Purpose and Functionality
`DataSourceUtils` is a core library class that acts as a factory for creating and managing HikariCP DataSource instances. It is the central component that enables:

1. **Multi-Tenant Database Routing**: Automatically determines which database to connect to based on:
   - Current tenant ID (from `TekionContextProvider`)
   - Current dealer ID (from `TekionContextProvider`)
   - Module name (e.g., ACCOUNTING_DEFAULT, GLOBAL)

2. **DataSource Caching**: Maintains a cache of DataSource instances keyed by:
   - Module name
   - Tenant ID
   - Dealer ID
   - Database type (primary vs read replica)

3. **Connection Pool Lifecycle Management**:
   - Creates HikariCP DataSource with appropriate configuration
   - Manages pool initialization and shutdown
   - Handles connection validation and leak detection

4. **Dynamic Configuration Application**:
   - Applies default configuration from `application.properties`
   - Overlays dealer-specific overrides from `IMySqlConfigPropProvider`
   - Configures HikariCP properties dynamically

### How JdbcTemplate is Created

The flow from repository to JdbcTemplate creation:

```
Repository.getJdbcTemplate()
    ↓
BaseDealerLevelMysqlRepository.getJdbcTemplate()
    ↓
DataSourceUtils.getDataSource(moduleName, tenantId, dealerId, options)
    ↓
[Check cache for existing DataSource]
    ↓
[If not cached] Create new HikariDataSource
    ↓
Apply configuration:
  - Default properties (mysql.default.min.connections, mysql.default.max.connections)
  - Dynamic overrides (IMySqlConfigPropProvider)
  - Scheduled executor service (hikariScheduledExecutorService)
    ↓
Cache DataSource
    ↓
Create JdbcTemplate with DataSource
    ↓
Return JdbcTemplate to repository
```

### HikariCP Configuration Applied

When DataSourceUtils creates a HikariDataSource, it configures:

```java
HikariConfig config = new HikariConfig();

// Database connection
config.setJdbcUrl(jdbcUrl);  // Determined from tenant/dealer context
config.setUsername(username);
config.setPassword(password);

// Pool sizing
config.setMinimumIdle(minConnections);  // From mysql.default.min.connections
config.setMaximumPoolSize(maxConnections);  // From mysql.default.max.connections or override

// Performance tuning
config.setConnectionTimeout(30000);  // 30 seconds
config.setIdleTimeout(600000);  // 10 minutes
config.setMaxLifetime(1800000);  // 30 minutes

// Prepared statements
config.setUseServerPrepStmts(useServerPreparedStatement);  // From dealer property

// Housekeeping
config.setScheduledExecutor(hikariScheduledExecutorService);  // Custom executor

// Metadata optimization
config.setUseInformationSchema(true);  // From mysql.config.useInformationSchema
```

## References

### Core Classes
- `com.tekion.core.mysql.DataSourceUtils`: Datasource factory and management
- `com.tekion.core.mysql.BaseDealerLevelMysqlRepository`: Dealer-level base repository
- `com.tekion.core.mysql.BaseGlobalMysqlRepository`: Global-level base repository
- `com.zaxxer.hikari.pool.HikariPool`: Connection pool implementation
- `com.tekion.core.mysql.lib.MysqlDaoImpl`: DAO implementation for MySQL operations

### Configuration
- `application.properties`: Default MySQL configuration
- `IMySqlConfigPropProvider`: Dynamic configuration provider
- `DpUtils`: Dealer property utilities for runtime configuration

### Key Files in Accounting Service
- `BeanFactory.java`: Defines hikariScheduledExecutorService bean
- `AccountingMigrationLauncher.java`: Implements IMySqlConfigPropProvider
- `BaseDealerLevelMysqlRepository`: Base class for dealer-level repositories
- `BaseGlobalMysqlRepository`: Base class for global-level repositories

---

**Document Purpose**: This context document is designed to be used as input for AI agents or developers who need to implement similar JDBC template factory patterns in other services or modules. It captures the complete architecture, patterns, and best practices used in the Accounting Service.

**Usage Instructions for AI Agents**:
1. Use this document to understand the complete flow from repository to database connection
2. Follow the repository implementation patterns for consistency
3. Apply the same configuration approach for HikariCP
4. Implement multi-tenancy using the same context-based routing
5. Use the transaction management patterns for data consistency
6. Apply performance optimization techniques as documented

