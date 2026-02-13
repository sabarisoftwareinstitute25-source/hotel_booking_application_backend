-- Add vendor_id column to hotel_vendors table to link with vendors table
-- This allows linking the vendor account (EIH2026V010004) with hotel registration (HV2026010001)

ALTER TABLE hotel_vendors
ADD COLUMN IF NOT EXISTS vendor_id VARCHAR(50);

-- Create index for faster lookups
CREATE INDEX IF NOT EXISTS idx_hotel_vendors_vendor_id ON hotel_vendors(vendor_id);

-- Add comment
COMMENT ON COLUMN hotel_vendors.vendor_id IS 'Reference to vendors table (EIH2026V010004). Links vendor account to hotel registration.';

-- Verify the change
SELECT 
    column_name, 
    data_type, 
    character_maximum_length,
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'hotel_vendors' AND column_name = 'vendor_id';

