# JDBC Template Factory Pattern - Documentation Index

## 📚 Overview

This documentation suite provides comprehensive information about how JdbcTemplate instances are created, managed, and used in the Accounting Service. The pattern uses HikariCP for connection pooling, supports multi-tenancy, and provides a clean repository abstraction layer.

## 📖 Documentation Files

### 1. **JDBC_TEMPLATE_FACTORY_CONTEXT.md** (Comprehensive Guide)
**Purpose**: Complete technical documentation for developers and AI agents

**Contents**:
- Architecture pattern explanation
- DataSource creation and management
- HikariCP configuration details
- Repository implementation patterns
- Database operations (CRUD, queries, transactions)
- Multi-tenancy and context management
- Performance optimization techniques
- DataSourceUtils deep dive
- Best practices and references

**When to use**: 
- Understanding the complete architecture
- Implementing similar patterns in other services
- Deep technical reference
- Training AI agents on the pattern

**Size**: 528 lines

---

### 2. **JDBC_TEMPLATE_QUICK_REFERENCE.md** (Quick Start Guide)
**Purpose**: Fast reference for common tasks and patterns

**Contents**:
- Quick start templates for creating repositories
- Common operations cheat sheet (insert, query, update, delete)
- Transaction management examples
- Configuration snippets
- Key constants and their usage
- Batch processing patterns
- Troubleshooting guide
- Best practices checklist

**When to use**:
- Quick lookup for syntax
- Copy-paste templates
- Daily development reference
- Onboarding new developers

**Size**: 151 lines

---

### 3. **JDBC_ARCHITECTURE_DIAGRAM.md** (Visual Architecture)
**Purpose**: ASCII diagrams showing system architecture and flows

**Contents**:
- Overall architecture flow (layers)
- DataSource creation flow
- Multi-tenancy routing diagram
- Configuration override flow
- Transaction management flow
- Component interaction diagram

**When to use**:
- Understanding system architecture visually
- Explaining the pattern to others
- Documentation and presentations
- Architecture reviews

**Size**: 150+ lines

---

### 4. **Interactive Mermaid Diagrams** (Visual Flows)
**Purpose**: Interactive visual diagrams for better understanding

**Diagrams Available**:
1. **JDBC Template Factory Architecture**: Shows the complete layer architecture from Service to Database
2. **DataSource Creation and Caching Flow**: Flowchart showing how DataSources are created and cached
3. **Multi-Tenancy Database Routing**: Shows how requests are routed to different databases

**When to use**:
- Visual learners
- Architecture presentations
- Understanding complex flows
- Team discussions

---

## 🚀 Quick Start

### For New Developers
1. Start with **JDBC_TEMPLATE_QUICK_REFERENCE.md** for immediate productivity
2. Review the **Mermaid Diagrams** to understand the architecture visually
3. Refer to **JDBC_TEMPLATE_FACTORY_CONTEXT.md** for deep understanding

### For AI Agents
1. Ingest **JDBC_TEMPLATE_FACTORY_CONTEXT.md** as primary context
2. Use **JDBC_TEMPLATE_QUICK_REFERENCE.md** for code generation templates
3. Reference **JDBC_ARCHITECTURE_DIAGRAM.md** for understanding flows

### For Architects
1. Review **JDBC_ARCHITECTURE_DIAGRAM.md** for system design
2. Study **JDBC_TEMPLATE_FACTORY_CONTEXT.md** for implementation details
3. Use **Mermaid Diagrams** for presentations

---

## 🔑 Key Concepts

### The "Factory" Pattern
There is **no explicit factory class** named "MysqlJdbcTemplateFactory". Instead:
- **DataSourceUtils** (core library) acts as the factory
- **Base repository classes** provide `getJdbcTemplate()` method
- **Inheritance-based pattern** for repository implementation

### Core Components
1. **DataSourceUtils**: Creates and caches HikariCP DataSource instances
2. **BaseDealerLevelMysqlRepository**: Base class for dealer-scoped repositories
3. **BaseGlobalMysqlRepository**: Base class for global/tenant-scoped repositories
4. **HikariCP**: Connection pool implementation
5. **IMySqlConfigPropProvider**: Dynamic configuration provider

### Multi-Tenancy
- Automatic routing based on `TekionContextProvider`
- Separate DataSource per module/tenant/dealer combination
- Support for read replicas via `TekMysqlRequestOptions.READER_DEFAULT`

---

## 📋 Common Use Cases

### Creating a New Repository
```java
@Component
public class MyRepositoryImpl extends BaseDealerLevelMysqlRepository<MyEntity> {
    public MyRepositoryImpl() {
        super(ACCOUNTING_DEFAULT);
    }
}
```
**See**: JDBC_TEMPLATE_QUICK_REFERENCE.md → "Quick Start: Creating a Repository"

### Executing a Query
```java
Criteria criteria = new Criteria(DEALER_ID).is(dealerId);
List<MyEntity> results = this.mysqlDao.queryList(getJdbcTemplate(), Query.fromCriteria(criteria), MyEntity.class);
```
**See**: JDBC_TEMPLATE_QUICK_REFERENCE.md → "Common Operations Cheat Sheet"

### Managing Transactions
```java
repository.executeInTransaction(() -> {
    repository.save(entity1);
    repository.update(entity2);
    return result;
});
```
**See**: JDBC_TEMPLATE_QUICK_REFERENCE.md → "Transaction Management"

---

## 🛠️ Configuration

### Default Configuration (application.properties)
```properties
mysql.default.min.connections=0
mysql.default.max.connections=20
mysql.config.useInformationSchema=true
```

### Dynamic Overrides (Per Dealer)
Configured via `IMySqlConfigPropProvider` bean and dealer properties

**See**: JDBC_TEMPLATE_FACTORY_CONTEXT.md → "DataSource Creation and Management"

---

## 📊 Architecture Summary

```
Service Layer
    ↓
Repository (extends BaseDealerLevelMysqlRepository)
    ↓
getJdbcTemplate() → DataSourceUtils
    ↓
HikariCP DataSource (cached)
    ↓
Physical Database (Primary/Replica/Global)
```

---

## 🎯 Best Practices

1. ✅ Always use `getJdbcTemplate()` from base class
2. ✅ Never create JdbcTemplate instances manually
3. ✅ Use batch operations for bulk inserts/updates
4. ✅ Keep transactions short and focused
5. ✅ Use read replicas for read-heavy operations
6. ✅ Handle null/empty query results
7. ✅ Apply index hints for performance-critical queries
8. ✅ Configure connection pool based on workload

**See**: JDBC_TEMPLATE_QUICK_REFERENCE.md → "Best Practices Checklist"

---

## 📞 Support & References

### Core Library Classes
- `com.tekion.core.mysql.DataSourceUtils`
- `com.tekion.core.mysql.BaseDealerLevelMysqlRepository`
- `com.tekion.core.mysql.BaseGlobalMysqlRepository`
- `com.zaxxer.hikari.pool.HikariPool`

### Key Files in Accounting Service
- `BeanFactory.java`: Defines hikariScheduledExecutorService bean
- `AccountingMigrationLauncher.java`: Implements IMySqlConfigPropProvider

**See**: JDBC_TEMPLATE_FACTORY_CONTEXT.md → "References"

---

**Last Updated**: December 22, 2024  
**Version**: 1.0  
**Maintained by**: Accounting Service Team

