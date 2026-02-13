-- Fix vendors table schema to match Vendor entity
-- This script adds missing columns and ensures the table structure matches the Java entity

-- Add missing columns if they don't exist
DO $$ 
BEGIN
    -- Add phone column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'vendors' AND column_name = 'phone') THEN
        ALTER TABLE vendors ADD COLUMN phone VARCHAR(20);
    END IF;

    -- Add password column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'vendors' AND column_name = 'password') THEN
        ALTER TABLE vendors ADD COLUMN password VARCHAR(255);
    END IF;

    -- Add status column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'vendors' AND column_name = 'status') THEN
        ALTER TABLE vendors ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE';
    END IF;

    -- Add updated_at column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'vendors' AND column_name = 'updated_at') THEN
        ALTER TABLE vendors ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Update existing columns to match entity (if needed)
-- Ensure created_at has default value
ALTER TABLE vendors 
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP,
    ALTER COLUMN created_at SET NOT NULL;

-- Add unique constraints if they don't exist
DO $$
BEGIN
    -- Add unique constraint on email if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_email') THEN
        ALTER TABLE vendors ADD CONSTRAINT unique_email UNIQUE (email);
    END IF;

    -- Add unique constraint on phone if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_phone') THEN
        ALTER TABLE vendors ADD CONSTRAINT unique_phone UNIQUE (phone);
    END IF;
END $$;

-- Create indexes if they don't exist
CREATE INDEX IF NOT EXISTS idx_vendors_email ON vendors(email);
CREATE INDEX IF NOT EXISTS idx_vendors_phone ON vendors(phone);
CREATE INDEX IF NOT EXISTS idx_vendors_status ON vendors(status);

-- Verify table structure
SELECT 
    column_name, 
    data_type, 
    character_maximum_length,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'vendors'
ORDER BY ordinal_position;

