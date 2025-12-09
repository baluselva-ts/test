# How Entity Classes Map to Supabase Table Names

## Overview

The mapping between Java entity classes and Supabase database tables happens through **SQL schema files** and **JOOQ code generation**. There is **NO automatic mapping** - you explicitly define the table name in the SQL file.

---

## The Mapping Flow

### 1. **Entity Class** (Java)
```java
// AssetUserPermissionMappingEntity.java
public class AssetUserPermissionMappingEntity extends BasePostgresEntity {
    private String permissionId;
    private String userId;
}
```

### 2. **SQL Schema File** (You create this)
```sql
-- src/main/resources/db/schema/asset_user_permission_mapping.sql
CREATE TABLE asset_user_permission_mapping (  -- ← TABLE NAME DEFINED HERE
    id BIGINT PRIMARY KEY,
    permission_id VARCHAR(255),
    user_id VARCHAR(255),
    ...
);
```

**Key Point**: The table name `asset_user_permission_mapping` is **explicitly defined** in the SQL `CREATE TABLE` statement.

### 3. **JOOQ Code Generation**
```bash
./gradlew generateJooq
```

JOOQ reads the SQL file and generates:

```java
// build/generated-src/jooq/.../tables/AssetUserPermissionMapping.java
public class AssetUserPermissionMapping extends TableImpl<AssetUserPermissionMappingRecord> {
    
    public static final AssetUserPermissionMapping ASSET_USER_PERMISSION_MAPPING = 
        new AssetUserPermissionMapping();
    
    public AssetUserPermissionMapping() {
        this(DSL.name("asset_user_permission_mapping"), null);  // ← Table name from SQL
    }
}
```

### 4. **Repository Implementation**
```java
// AssetUserPermissionMappingRepoImpl.java
@Override
protected Table<AssetUserPermissionMappingRecord> getTable() {
    return AssetUserPermissionMapping.ASSET_USER_PERMISSION_MAPPING;  // ← Uses generated constant
}
```

### 5. **Runtime Queries**
When you call repository methods, JOOQ generates SQL like:
```sql
SELECT * FROM asset_user_permission_mapping WHERE id = ?
```

The table name comes from the generated JOOQ class, which got it from your SQL file.

---

## Naming Convention

### Recommended Pattern:

| Component | Naming Convention | Example |
|-----------|------------------|---------|
| **Entity Class** | PascalCase + Entity suffix | `AssetUserPermissionMappingEntity` |
| **SQL File** | snake_case.sql | `asset_user_permission_mapping.sql` |
| **Table Name** | snake_case (in SQL) | `asset_user_permission_mapping` |
| **JOOQ Class** | PascalCase (auto-generated) | `AssetUserPermissionMapping` |
| **JOOQ Constant** | UPPER_SNAKE_CASE (auto-generated) | `ASSET_USER_PERMISSION_MAPPING` |

### Why This Convention?

1. **Java**: Uses PascalCase (standard Java naming)
2. **SQL/Database**: Uses snake_case (PostgreSQL convention)
3. **JOOQ**: Automatically converts between the two

---

## Step-by-Step: Adding a New Entity

Let's say you want to add a `RoleEntity` that maps to a `role` table:

### Step 1: Create the Entity Class
```java
// src/main/java/.../entity/RoleEntity.java
public class RoleEntity extends BasePostgresEntity {
    private String personaId;
    private String permissionEncoding;
}
```

### Step 2: Create the SQL Schema File
```sql
-- src/main/resources/db/schema/role.sql
CREATE TABLE role (  -- ← Choose your table name here
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN,
    is_archived BOOLEAN,
    is_custom BOOLEAN,
    is_read_only BOOLEAN,
    is_internal BOOLEAN,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    persona_id VARCHAR(255),
    permission_encoding TEXT
);
```

### Step 3: Generate JOOQ Code
```bash
./gradlew generateJooq
```

This creates:
- `build/generated-src/jooq/.../tables/Role.java`
- `build/generated-src/jooq/.../tables/records/RoleRecord.java`

### Step 4: Implement Repository
```java
@Repository
public class RoleRepoImpl extends BasePostgresRepoImpl<RoleRecord, RoleEntity, RoleDomain> {
    
    private final DSLContext dslContext;
    
    @Override
    protected DSLContext getDsl() {
        return dslContext;
    }
    
    @Override
    protected Table<RoleRecord> getTable() {
        return Role.ROLE;  // ← Uses generated constant
    }
}
```

### Step 5: Create Table in Supabase
Run this SQL in Supabase SQL Editor:
```sql
CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    ...
);
```

**Important**: The table name in Supabase **MUST match** the table name in your SQL schema file.

---

## Common Questions

### Q: Can I use a different table name than the entity name?

**A: Yes!** The table name is defined in the SQL file, not derived from the entity class name.

Example:
```sql
-- Entity: AssetUserPermissionMappingEntity
-- Table: user_asset_permissions (different name)

CREATE TABLE user_asset_permissions (  -- ← Any name you want
    ...
);
```

Then JOOQ will generate `UserAssetPermissions.java` with constant `USER_ASSET_PERMISSIONS`.

### Q: What if I rename the table?

**A: Update the SQL file and regenerate:**

1. Update `src/main/resources/db/schema/your_table.sql`
2. Run `./gradlew generateJooq`
3. Update the table name in Supabase
4. Restart your application

### Q: Do I need to match Java field names to column names?

**A: No, but it's recommended.** JOOQ handles the mapping through the generated code. However, for clarity:

- Java field: `permissionId` (camelCase)
- SQL column: `permission_id` (snake_case)
- JOOQ constant: `PERMISSION_ID` (UPPER_SNAKE_CASE)

### Q: Can I have multiple entities use the same table?

**A: Technically yes, but not recommended.** Each entity should map to one table. If you need different views of the same data, use different domain classes, not entities.

---

## Summary

✅ **Table name is defined in SQL file** - Not derived from entity class  
✅ **JOOQ generates code from SQL** - Reads table name from CREATE TABLE statement  
✅ **Repository uses generated constant** - Type-safe reference to table  
✅ **Supabase table must match SQL file** - Same table name in both places  
✅ **No manual configuration needed** - JOOQ handles everything after SQL file is created  

---

## Quick Reference

```
Entity Class Name:     AssetUserPermissionMappingEntity
         ↓
SQL File Name:         asset_user_permission_mapping.sql
         ↓
Table Name (in SQL):   asset_user_permission_mapping
         ↓
JOOQ Generated Class:  AssetUserPermissionMapping
         ↓
JOOQ Constant:         ASSET_USER_PERMISSION_MAPPING
         ↓
Supabase Table:        asset_user_permission_mapping
```

The **SQL file** is the source of truth for the table name!

