-- AssetUserPermissionMappingEntity table schema
CREATE TABLE asset_user_permission_mapping (
    -- From BaseEntity (inherited fields)
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
    
    -- AssetUserPermissionMappingEntity specific fields
    permission_id VARCHAR(255),
    user_id VARCHAR(255)
);

