-- Migration script to add property_type column to hotel_vendors table
-- This supports Property Partner registration for Hotel, Villa, Apartment, and Resort

-- Add property_type column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'hotel_vendors' 
        AND column_name = 'property_type'
    ) THEN
        ALTER TABLE hotel_vendors 
        ADD COLUMN property_type VARCHAR(20) NOT NULL DEFAULT 'Hotel';
        
        -- Update comment
        COMMENT ON COLUMN hotel_vendors.property_type IS 'Property type: Hotel, Villa, Apartment, or Resort';
    END IF;
END $$;

-- Verify the column was added
SELECT column_name, data_type, character_maximum_length, is_nullable, column_default
FROM information_schema.columns
WHERE table_name = 'hotel_vendors' 
AND column_name = 'property_type';

