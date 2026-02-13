-- QUICK FIX: Update vendors table structure
-- Run this script to fix the vendors table to match the Vendor entity

-- Step 1: Drop existing table (WARNING: This deletes all data!)
-- Uncomment the next line ONLY if you want to delete existing data
-- DROP TABLE IF EXISTS vendors CASCADE;

-- Step 2: Create the correct vendors table
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

-- Step 3: Drop old unique constraints if they exist
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_email') THEN
        ALTER TABLE vendors DROP CONSTRAINT unique_email;
    END IF;
    
    IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_phone') THEN
        ALTER TABLE vendors DROP CONSTRAINT unique_phone;
    END IF;
END $$;

-- Step 4: Create unique indexes (allowing NULL values)
-- PostgreSQL allows multiple NULL values in unique indexes
DROP INDEX IF EXISTS unique_vendors_email;
DROP INDEX IF EXISTS unique_vendors_phone;
CREATE UNIQUE INDEX unique_vendors_email ON vendors(email) WHERE email IS NOT NULL;
CREATE UNIQUE INDEX unique_vendors_phone ON vendors(phone) WHERE phone IS NOT NULL;

-- Step 5: Create regular indexes for faster searches
DROP INDEX IF EXISTS idx_vendors_email;
DROP INDEX IF EXISTS idx_vendors_phone;
DROP INDEX IF EXISTS idx_vendors_status;
CREATE INDEX idx_vendors_email ON vendors(email);
CREATE INDEX idx_vendors_phone ON vendors(phone);
CREATE INDEX idx_vendors_status ON vendors(status);

-- Step 6: Verify table structure
SELECT 
    column_name, 
    data_type, 
    character_maximum_length,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'vendors'
ORDER BY ordinal_position;

-- Expected columns:
-- vendor_id (VARCHAR 50, NOT NULL, PRIMARY KEY)
-- full_name (VARCHAR 100, NOT NULL)
-- business_name (VARCHAR 150, NOT NULL)
-- phone (VARCHAR 20, NULLABLE)
-- email (VARCHAR 150, NULLABLE)
-- password (VARCHAR 255, NULLABLE)
-- status (VARCHAR 20, DEFAULT 'ACTIVE')
-- created_at (TIMESTAMP, NOT NULL, DEFAULT CURRENT_TIMESTAMP)
-- updated_at (TIMESTAMP, NULLABLE)

