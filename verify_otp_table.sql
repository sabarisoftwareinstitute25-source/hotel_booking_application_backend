-- Verify and fix OTP table structure
-- Run this to check and fix the otps table

-- Check if table exists
SELECT 'Checking if otps table exists...' as info;
SELECT EXISTS (
    SELECT FROM information_schema.tables 
    WHERE table_schema = 'public' 
    AND table_name = 'otps'
) as table_exists;

-- Check table structure
SELECT 'Current table structure:' as info;
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name = 'otps'
ORDER BY ordinal_position;

-- Add missing 'used' column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'used'
    ) THEN
        ALTER TABLE otps ADD COLUMN used BOOLEAN NOT NULL DEFAULT FALSE;
        RAISE NOTICE 'Added column: used';
    ELSE
        RAISE NOTICE 'Column "used" already exists';
    END IF;
END $$;

-- Add missing 'attempts' column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'attempts'
    ) THEN
        ALTER TABLE otps ADD COLUMN attempts INTEGER NOT NULL DEFAULT 0;
        RAISE NOTICE 'Added column: attempts';
    ELSE
        RAISE NOTICE 'Column "attempts" already exists';
    END IF;
END $$;

-- Verify final structure
SELECT 'Final table structure:' as info;
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name = 'otps'
ORDER BY ordinal_position;

-- Test insert
INSERT INTO otps (phone, code, created_at, expires_at, used, attempts)
VALUES (
    '+919876543210',
    '123456',
    NOW(),
    NOW() + INTERVAL '10 minutes',
    false,
    0
);

-- Verify insert
SELECT 'Test data inserted:' as info;
SELECT * FROM otps WHERE phone = '+919876543210';

-- Cleanup test
DELETE FROM otps WHERE phone = '+919876543210';

SELECT 'Table structure verified and fixed!' as result;

