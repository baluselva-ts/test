# JDBC Template Factory - Quick Reference Guide

## Quick Start: Creating a Repository

### Dealer-Level Repository (Most Common)
```java
@Component
public class MyEntityRepositoryImpl 
    extends BaseDealerLevelMysqlRepository<MyEntity> 
    implements MyEntityRepository {
    
    public MyEntityRepositoryImpl() {
        super(ACCOUNTING_DEFAULT);  // or ModuleName.ACCOUNTING_DEFAULT.name()
    }
    
    @Override
    public MyEntity save(MyEntity entity) {
        this.mysqlDao.insert(getJdbcTemplate(), entity);
        return entity;
    }
    
    @Override
    public List<MyEntity> findByDealerId(String dealerId) {
        Criteria criteria = new Criteria(DEALER_ID).is(dealerId);
        Query query = Query.fromCriteria(criteria);
        return this.mysqlDao.queryList(getJdbcTemplate(), query, MyEntity.class);
    }
}
```

### Global-Level Repository
```java
@Component
public class GlobalEntityRepositoryImpl 
    extends BaseGlobalMysqlRepository<GlobalEntity> 
    implements GlobalEntityRepository {
    
    public GlobalEntityRepositoryImpl() {
        super(KEYWORD_CONFIG_GLOBAL_ASSET_TYPE);
    }
}
```

### Read Replica Repository
```java
@Component
public class MyEntityReadReplicaRepositoryImpl 
    extends BaseDealerLevelMysqlRepository<MyEntity> {
    
    public MyEntityReadReplicaRepositoryImpl() {
        super(ACCOUNTING_DEFAULT, TekMysqlRequestOptions.READER_DEFAULT);
    }
}
```

## Common Operations Cheat Sheet

### Insert
```java
// Single
this.mysqlDao.insert(getJdbcTemplate(), entity);

// Batch
this.mysqlDao.insertBatch(getJdbcTemplate(), entityList);
```

### Query
```java
// By criteria
Criteria c = new Criteria(FIELD_NAME).is(value);
List<Entity> results = this.mysqlDao.queryList(getJdbcTemplate(), Query.fromCriteria(c), Entity.class);

// Single object
Entity entity = this.mysqlDao.queryObject(getJdbcTemplate(), Query.fromCriteria(c), Entity.class);
```

### Update
```java
Criteria c = new Criteria(ID).is(id);
Update u = new Update().set(FIELD_NAME, newValue);
this.mysqlDao.update(getJdbcTemplate(), Query.fromCriteria(c), u, Entity.class);
```

### Delete (Soft)
```java
Criteria c = new Criteria(ID).is(id);
Update u = new Update().set(DELETED, true);
this.mysqlDao.update(getJdbcTemplate(), Query.fromCriteria(c), u, Entity.class);
```

### Complex SQL with Named Parameters
```java
NamedParameterJdbcTemplate namedTemplate = 
    new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());

Map<String, Object> params = Map.of("dealerId", dealerId, "status", status);
List<Map<String, Object>> results = namedTemplate.queryForList(sqlQuery, params);
```

## Transaction Management

### Simple Transaction
```java
repository.executeInTransaction(() -> {
    repository.save(entity1);
    repository.update(entity2);
    return result;
});
```

### Transaction with After-Commit Hook
```java
repository.executeInTransaction(
    () -> {
        // Main transaction
        return mainResult;
    },
    () -> {
        // After commit (e.g., send notification)
        notificationService.send();
        return null;
    }
);
```

## Configuration Quick Reference

### application.properties
```properties
mysql.config.useInformationSchema=true
mysql.query.disableColumnMetadata=true
mysql.default.min.connections=0
mysql.default.max.connections=20
```

### Dynamic Configuration Bean
```java
@Bean
@Primary
public IMySqlConfigPropProvider doGetIMySqlConfigPropProvider() {
    return fetchRequest -> {
        SqlPropertyResponseDto dto = new SqlPropertyResponseDto();
        
        int poolSize = DpUtils.getSqlPropForConnectionPoolSize();
        if (poolSize != -1) {
            dto.setMaxConnections(SqlOverride.getEnabledOverride(poolSize));
        }
        
        return dto;
    };
}
```

### HikariCP Executor Service Bean
```java
@Bean
public ScheduledExecutorService hikariScheduledExecutorService() {
    boolean isProduction = TEnvConstants.CLUSTER_TYPE.contains("prod");
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
        isProduction ? 100 : 20,
        new UtilityElf.DefaultThreadFactory("Hikari housekeeper", true),
        new ThreadPoolExecutor.DiscardPolicy()
    );
    executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
    executor.setRemoveOnCancelPolicy(true);
    return executor;
}
```

## Key Constants

| Constant | Usage |
|----------|-------|
| `ACCOUNTING_DEFAULT` | Standard dealer-level module |
| `KEYWORD_CONFIG_GLOBAL_ASSET_TYPE` | Global/tenant-level module |
| `TekMysqlRequestOptions.READER_DEFAULT` | Route to read replica |

## Common Patterns

### Batch Processing
```java
BatchIterator<Entity> iterator = new BatchIterator<>(
    new BatchDataRetriever<Entity>() {
        @Override
        public List<Entity> fetch(int offset, int rows, Entity lastObject) {
            return fetchBatch(offset, rows);
        }
    },
    batchSize
);

while (iterator.hasNext()) {
    processBatch(iterator.next());
}
```

### Index Hints
```java
String indexName = indexManager.getIndexName(
    (MysqlDaoImpl) mysqlDao, 
    getJdbcTemplate(), 
    Entity.class, 
    Arrays.asList("column1", "column2")
);
String sql = "SELECT * FROM table FORCE INDEX(" + indexName + ") WHERE ...";
```

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Connection pool exhausted | Increase `mysql.default.max.connections` or use dealer-specific override |
| Slow queries | Use read replicas, add index hints, optimize batch size |
| Transaction timeout | Keep transactions short, move non-critical work to after-commit hooks |
| Multi-tenancy issues | Verify `TekionContextProvider` has correct tenant/dealer ID |

## Best Practices Checklist

- [ ] Use `getJdbcTemplate()` from base class (never create manually)
- [ ] Extend correct base class (Dealer vs Global)
- [ ] Use batch operations for bulk inserts/updates
- [ ] Keep transactions short and focused
- [ ] Use read replicas for read-heavy operations
- [ ] Handle null/empty query results
- [ ] Use `NamedParameterJdbcTemplate` for complex SQL
- [ ] Apply index hints for performance-critical queries
- [ ] Configure connection pool based on workload
- [ ] Use after-commit hooks for async operations

---

**See JDBC_TEMPLATE_FACTORY_CONTEXT.md for comprehensive documentation**

