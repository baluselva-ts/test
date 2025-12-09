# Supabase Setup Guide for AssetUserPermissionMapping

## 🚀 Quick Start

Follow these steps to set up Supabase and test the AssetUserPermissionMapping end-to-end.

---

## Step 1: Create Supabase Project

1. Go to [https://app.supabase.com](https://app.supabase.com)
2. Sign in or create an account
3. Click **"New Project"**
4. Fill in:
   - **Name**: `rolesandpermissionsv2` (or any name you prefer)
   - **Database Password**: Choose a strong password (save this!)
   - **Region**: Choose closest to you
5. Click **"Create new project"**
6. Wait 2-3 minutes for the project to be ready

---

## Step 2: Get Database Connection Details

1. In your Supabase project, go to **Settings** (gear icon) → **Database**
2. Scroll down to **"Connection string"** section
3. Select **"URI"** mode
4. Copy the connection string (it looks like):
   ```
   postgresql://postgres:[YOUR-PASSWORD]@db.xxxxxxxxxxxxx.supabase.co:5432/postgres
   ```
5. Note down:
   - **Host**: `db.xxxxxxxxxxxxx.supabase.co`
   - **Username**: `postgres`
   - **Password**: The password you set in Step 1

---

## Step 3: Create ALL Database Tables

1. In your Supabase project, go to **SQL Editor** (left sidebar)
2. Click **"New query"**
3. Copy and paste the contents of `src/main/resources/db/migration/V1__create_all_tables.sql`
4. Click **"Run"** (or press Ctrl+Enter / Cmd+Enter)
5. You should see: **"Success. No rows returned"**

This will create all 9 tables:
- department
- module
- persona
- permission
- role
- asset_user_permission_mapping
- persona_permission_mapping
- role_permission_mapping
- user_role_mapping

To verify the tables were created:
1. Go to **Table Editor** (left sidebar)
2. You should see all 9 tables in the list

---

## Step 4: Configure Environment Variables

### Option A: Using Environment Variables (Recommended)

Create a `.env` file in the project root:

```bash
cp .env.example .env
```

Edit `.env` and fill in your Supabase credentials:

```properties
SUPABASE_DB_URL=jdbc:postgresql://db.xxxxxxxxxxxxx.supabase.co:5432/postgres?sslmode=require
SUPABASE_DB_USERNAME=postgres
SUPABASE_DB_PASSWORD=your-actual-password
```

**Important**: Replace:
- `xxxxxxxxxxxxx` with your actual Supabase project ID
- `your-actual-password` with your database password

### Option B: Using application.properties (Not Recommended for Production)

Edit `src/main/resources/application.properties` and replace the default values:

```properties
spring.datasource.url=jdbc:postgresql://db.xxxxxxxxxxxxx.supabase.co:5432/postgres?sslmode=require
spring.datasource.username=postgres
spring.datasource.password=your-actual-password
```

---

## Step 5: Run the Application

### Using Gradle:

```bash
# Export environment variables (if using .env)
export $(cat .env | xargs)

# Run the application
./gradlew bootRun
```

### Using IntelliJ IDEA:

1. Open **Run** → **Edit Configurations**
2. Select your Spring Boot configuration
3. Add environment variables:
   - `SUPABASE_DB_URL=jdbc:postgresql://db.xxxxxxxxxxxxx.supabase.co:5432/postgres?sslmode=require`
   - `SUPABASE_DB_USERNAME=postgres`
   - `SUPABASE_DB_PASSWORD=your-actual-password`
4. Click **OK** and run the application

---

## Step 6: Test the API

Once the application starts, you can test the endpoints:

### 1. Access Swagger UI

Open your browser and go to:
```
http://localhost:8080/swagger-ui/index.html
```

Look for **"AssetUserPermissionMapping"** section.

### 2. Create a new AssetUserPermissionMapping

**POST** `http://localhost:8080/v1/asset-user-permission-mapping`

Request body:
```json
{
  "name": "Test Permission Mapping",
  "label": "Test Label",
  "permissionId": "perm-123",
  "userId": "user-456"
}
```

### 3. Get all AssetUserPermissionMappings

**GET** `http://localhost:8080/v1/asset-user-permission-mapping`

### 4. Get by ID

**GET** `http://localhost:8080/v1/asset-user-permission-mapping/{id}`

Replace `{id}` with the ID returned from the create request.

---

## Troubleshooting

### Connection Refused Error

**Error**: `Connection refused` or `Connection timeout`

**Solution**:
- Make sure your Supabase project is running
- Check that you're using the correct connection string
- Verify that `?sslmode=require` is added to the URL

### Authentication Failed Error

**Error**: `password authentication failed for user "postgres"`

**Solution**:
- Double-check your database password
- Reset the database password in Supabase Settings → Database → Reset Database Password

### Table Not Found Error

**Error**: `relation "asset_user_permission_mapping" does not exist`

**Solution**:
- Run the SQL script from Step 3 again
- Verify the table exists in Table Editor

