-- Fix hotel_vendors table schema to match HotelVendor entity
-- This script updates the table to use 'registration_status' instead of 'status'

-- Step 1: Add registration_status column if it doesn't exist
ALTER TABLE hotel_vendors 
ADD COLUMN IF NOT EXISTS registration_status VARCHAR(20) DEFAULT 'PENDING';

-- Step 2: Copy data from status to registration_status if status exists
UPDATE hotel_vendors 
SET registration_status = status 
WHERE registration_status IS NULL AND status IS NOT NULL;

-- Step 3: Drop old status column if it exists
ALTER TABLE hotel_vendors 
DROP COLUMN IF EXISTS status;

-- Step 4: Recreate index with correct column name
DROP INDEX IF EXISTS idx_hotel_vendors_status;
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_status ON hotel_vendors(registration_status);

-- Step 5: Add missing columns if they don't exist (for bank details)
ALTER TABLE hotel_vendors 
ADD COLUMN IF NOT EXISTS account_holder_name VARCHAR(100),
ADD COLUMN IF NOT EXISTS bank_name VARCHAR(100),
ADD COLUMN IF NOT EXISTS account_number VARCHAR(30), 
ADD COLUMN IF NOT EXISTS ifsc_code VARCHAR(11),
ADD COLUMN IF NOT EXISTS branch VARCHAR(100),
ADD COLUMN IF NOT EXISTS account_type VARCHAR(20),
ADD COLUMN IF NOT EXISTS signature_name VARCHAR(100),
ADD COLUMN IF NOT EXISTS declaration_name VARCHAR(100),
ADD COLUMN IF NOT EXISTS declaration_date TIMESTAMP,
ADD COLUMN IF NOT EXISTS declaration_accepted BOOLEAN DEFAULT false,
ADD COLUMN IF NOT EXISTS trade_license VARCHAR(50);

-- Step 6: Verify table structure
SELECT 
    column_name, 
    data_type, 
    character_maximum_length,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'hotel_vendors'
ORDER BY ordinal_position;

