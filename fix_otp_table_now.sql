-- IMMEDIATE FIX: Add missing columns to otps table
-- Run this NOW to fix the OTP storage issue

-- Step 1: Add 'used' column if missing
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'used'
    ) THEN
        ALTER TABLE otps ADD COLUMN used BOOLEAN NOT NULL DEFAULT FALSE;
        RAISE NOTICE '✅ Added column: used';
    ELSE
        RAISE NOTICE 'Column "used" already exists';
    END IF;
END $$;

-- Step 2: Add 'attempts' column if missing
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'attempts'
    ) THEN
        ALTER TABLE otps ADD COLUMN attempts INTEGER NOT NULL DEFAULT 0;
        RAISE NOTICE '✅ Added column: attempts';
    ELSE
        RAISE NOTICE 'Column "attempts" already exists';
    END IF;
END $$;

-- Step 3: Verify all columns exist
SELECT 'Verifying table structure...' as info;
SELECT 
    column_name, 
    data_type,
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name = 'otps'
ORDER BY ordinal_position;

-- Step 4: Test insert
INSERT INTO otps (phone, code, created_at, expires_at, used, attempts)
VALUES (
    '+919876543210',
    '123456',
    NOW(),
    NOW() + INTERVAL '10 minutes',
    false,
    0
);

-- Step 5: Verify insert worked
SELECT 'Test OTP inserted:' as info;
SELECT * FROM otps WHERE phone = '+919876543210';

-- Step 6: Cleanup test data
DELETE FROM otps WHERE phone = '+919876543210';

SELECT '✅ Table fixed! OTP storage should work now.' as result;

