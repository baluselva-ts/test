-- Complete database schema migration for Supabase
-- Run this script in your Supabase SQL Editor

-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS user_role_mapping CASCADE;
DROP TABLE IF EXISTS role_permission_mapping CASCADE;
DROP TABLE IF EXISTS persona_permission_mapping CASCADE;
DROP TABLE IF EXISTS asset_user_permission_mapping CASCADE;
DROP TABLE IF EXISTS permission CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS persona CASCADE;
DROP TABLE IF EXISTS module CASCADE;
DROP TABLE IF EXISTS department CASCADE;

-- Create department table
CREATE TABLE department (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255)
);

-- Create module table
CREATE TABLE module (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255)
);

-- Create persona table
CREATE TABLE persona (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    permission_encoding VARCHAR(255)
);

-- Create permission table
CREATE TABLE permission (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    module_id VARCHAR(255),
    department_id VARCHAR(255)
);

-- Create role table
CREATE TABLE role (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    persona_id VARCHAR(255),
    permission_encoding VARCHAR(255)
);

-- Create asset_user_permission_mapping table
CREATE TABLE asset_user_permission_mapping (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    permission_id VARCHAR(255),
    user_id VARCHAR(255)
);

-- Create persona_permission_mapping table
CREATE TABLE persona_permission_mapping (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    persona_id VARCHAR(255),
    permission_id VARCHAR(255)
);



-- Create role_permission_mapping table
CREATE TABLE role_permission_mapping (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    role_id VARCHAR(255),
    permission_id VARCHAR(255)
);

-- Create user_role_mapping table
CREATE TABLE user_role_mapping (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    label VARCHAR(255),
    scope_id VARCHAR(255),
    is_deleted BOOLEAN DEFAULT false,
    is_archived BOOLEAN DEFAULT false,
    is_custom BOOLEAN DEFAULT false,
    is_read_only BOOLEAN DEFAULT false,
    is_internal BOOLEAN DEFAULT false,
    version BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    last_updated_at TIMESTAMP,
    last_updated_by VARCHAR(255),
    user_id VARCHAR(255),
    primary_role_id VARCHAR(255),
    secondary_role_ids VARCHAR(255)[]
);

-- Create indexes for better query performance
CREATE INDEX idx_department_scope_id ON department(scope_id);
CREATE INDEX idx_department_is_deleted ON department(is_deleted);

CREATE INDEX idx_module_scope_id ON module(scope_id);
CREATE INDEX idx_module_is_deleted ON module(is_deleted);

CREATE INDEX idx_persona_scope_id ON persona(scope_id);
CREATE INDEX idx_persona_is_deleted ON persona(is_deleted);

CREATE INDEX idx_permission_scope_id ON permission(scope_id);
CREATE INDEX idx_permission_is_deleted ON permission(is_deleted);
CREATE INDEX idx_permission_module_id ON permission(module_id);
CREATE INDEX idx_permission_department_id ON permission(department_id);

CREATE INDEX idx_role_scope_id ON role(scope_id);
CREATE INDEX idx_role_is_deleted ON role(is_deleted);
CREATE INDEX idx_role_persona_id ON role(persona_id);

CREATE INDEX idx_asset_user_perm_scope_id ON asset_user_permission_mapping(scope_id);
CREATE INDEX idx_asset_user_perm_is_deleted ON asset_user_permission_mapping(is_deleted);
CREATE INDEX idx_asset_user_perm_user_id ON asset_user_permission_mapping(user_id);
CREATE INDEX idx_asset_user_perm_permission_id ON asset_user_permission_mapping(permission_id);

CREATE INDEX idx_persona_perm_scope_id ON persona_permission_mapping(scope_id);
CREATE INDEX idx_persona_perm_is_deleted ON persona_permission_mapping(is_deleted);
CREATE INDEX idx_persona_perm_persona_id ON persona_permission_mapping(persona_id);
CREATE INDEX idx_persona_perm_permission_id ON persona_permission_mapping(permission_id);

CREATE INDEX idx_role_perm_scope_id ON role_permission_mapping(scope_id);
CREATE INDEX idx_role_perm_is_deleted ON role_permission_mapping(is_deleted);
CREATE INDEX idx_role_perm_role_id ON role_permission_mapping(role_id);
CREATE INDEX idx_role_perm_permission_id ON role_permission_mapping(permission_id);

CREATE INDEX idx_user_role_scope_id ON user_role_mapping(scope_id);
CREATE INDEX idx_user_role_is_deleted ON user_role_mapping(is_deleted);
CREATE INDEX idx_user_role_user_id ON user_role_mapping(user_id);
CREATE INDEX idx_user_role_primary_role_id ON user_role_mapping(primary_role_id);
