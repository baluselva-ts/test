# JOOQ Code Generation for AssetUserPermissionMappingEntity

## ✅ Setup Complete!

JOOQ is configured to generate code from SQL schema files **on-demand** (manual generation only).

### SQL Schema Files

Add your table schemas to: `src/main/resources/db/schema/*.sql`

✅ **Already created**: `asset_user_permission_mapping.sql` with the schema for AssetUserPermissionMappingEntity.

### ⚠️ IMPORTANT: Manual Generation Only

**JOOQ will ONLY generate code for tables defined in SQL files.** To avoid deleting existing generated code:

1. **Only run generation when you want to update JOOQ code**
2. **Add SQL files for ALL tables you want to generate**, not just one

```bash
# Generate JOOQ code (only run when needed)
./gradlew generateJooq
```

JOOQ will:
1. Read all SQL files from `src/main/resources/db/schema/`
2. Parse the table definitions using an in-memory H2 database
3. Generate type-safe classes in `build/generated-src/jooq/`
4. **Replace all previously generated code** with only the tables from SQL files

**Note**: Generated code is in the `build/` directory and is automatically included in compilation.

### Generated Files

Located in `build/generated-src/jooq/com/tekion/rolesandpermissionsv2/jooq/generated/`:

- `Tables.java` - Table constants
- `tables/AssetUserPermissionMapping.java` - Table definition
- `tables/records/AssetUserPermissionMappingRecord.java` - Record class for queries

### Usage in Repository

```java
import static com.tekion.rolesandpermissionsv2.jooq.generated.Tables.ASSET_USER_PERMISSION_MAPPING;
import com.tekion.rolesandpermissionsv2.jooq.generated.tables.records.AssetUserPermissionMappingRecord;

@Repository
public class AssetUserPermissionMappingRepoImpl extends BasePostgresRepoImpl<
        AssetUserPermissionMappingRecord,
        AssetUserPermissionMappingEntity,
        AssetUserPermissionMappingDomain> {

    @Override
    protected Table<AssetUserPermissionMappingRecord> getTable() {
        return ASSET_USER_PERMISSION_MAPPING;
    }
    
    // Type-safe queries
    public List<AssetUserPermissionMappingDomain> findByUserId(String userId) {
        return getDsl()
            .selectFrom(ASSET_USER_PERMISSION_MAPPING)
            .where(ASSET_USER_PERMISSION_MAPPING.USER_ID.eq(userId))
            .and(ASSET_USER_PERMISSION_MAPPING.IS_DELETED.eq(false))
            .fetch()
            .map(record -> mapper.toSecond(record.into(AssetUserPermissionMappingEntity.class)));
    }
}
```

### Adding More Tables

Just create more SQL files in `src/main/resources/db/schema/`:

```sql
-- src/main/resources/db/schema/my_other_table.sql
CREATE TABLE my_other_table (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);
```

Then run:
```bash
./gradlew generateJooq
```

**Note**: JOOQ will generate code for ALL tables in `src/main/resources/db/schema/*.sql` and replace the entire generated folder.

### Workflow

1. **Create/Update SQL files** in `src/main/resources/db/schema/`
2. **Run** `./gradlew generateJooq` when you want to update generated code
3. **Don't run** `generateJooq` during normal builds - it's manual only
4. **Generated code is in `build/`** - no need to commit (it's regenerated on build if needed)

### Why Manual Generation?

- JOOQ generates code ONLY for tables in SQL files
- Generated code is in `build/` directory, separate from your source code
- Manual generation gives you control over when to regenerate
- **Your source code is safe** - JOOQ can't delete it anymore!

### Important Safety Features

✅ **Generated code is in `build/generated-src/jooq/`** - completely separate from `src/`
✅ **Your source code in `src/main/java/` is safe** - JOOQ won't touch it
✅ **Manual generation only** - won't run on every build

That's it! JOOQ reads your SQL schema files - no live database connection needed.

