-- Migration script to fix vendors table structure
-- This script will:
-- 1. Drop the old vendors table if it exists with wrong structure
-- 2. Create the new vendors table with correct structure for Account Details

-- Step 1: Drop the old table if it exists (BE CAREFUL - this deletes all data!)
-- Uncomment the line below ONLY if you want to delete existing data
-- DROP TABLE IF EXISTS vendors CASCADE;

-- Step 2: Create the new vendors table with correct structure
CREATE TABLE IF NOT EXISTS vendors (
    vendor_id VARCHAR(50) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    business_name VARCHAR(150) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(150),
    password VARCHAR(255),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Step 3: Add unique constraints (only if they don't exist)
DO $$
BEGIN
    -- Drop existing constraints if they exist
    IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_email') THEN
        ALTER TABLE vendors DROP CONSTRAINT unique_email;
    END IF;
    
    IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_phone') THEN
        ALTER TABLE vendors DROP CONSTRAINT unique_phone;
    END IF;
    
    -- Add unique constraints (allowing NULL values)
    -- Note: PostgreSQL allows multiple NULL values in UNIQUE columns
    CREATE UNIQUE INDEX IF NOT EXISTS unique_vendors_email ON vendors(email) WHERE email IS NOT NULL;
    CREATE UNIQUE INDEX IF NOT EXISTS unique_vendors_phone ON vendors(phone) WHERE phone IS NOT NULL;
END $$;

-- Step 4: Create indexes for faster searches
CREATE INDEX IF NOT EXISTS idx_vendors_email ON vendors(email);
CREATE INDEX IF NOT EXISTS idx_vendors_phone ON vendors(phone);
CREATE INDEX IF NOT EXISTS idx_vendors_status ON vendors(status);

-- Step 5: Verify table structure
SELECT 
    column_name, 
    data_type, 
    character_maximum_length,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'vendors'
ORDER BY ordinal_position;

-- Expected output should show:
-- vendor_id, full_name, business_name, phone, email, password, status, created_at, updated_at

