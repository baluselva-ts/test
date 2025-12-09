-- PersonaEntity table schema
CREATE TABLE persona (
    -- From BaseEntity (inherited fields)
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

    -- PersonaEntity specific fields
    permission_encoding VARCHAR(255)
);

