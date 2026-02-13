-- ========================================
-- COMPLETE OTP FIX - Database, Backend, Frontend
-- ========================================
-- Run this script to fix all OTP issues

-- Step 1: Ensure otps table exists with all required columns
CREATE TABLE IF NOT EXISTS otps (
    id BIGSERIAL PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    code VARCHAR(6) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    attempts INTEGER NOT NULL DEFAULT 0
);

-- Step 2: Add missing columns if table exists but columns are missing
DO $$
BEGIN
    -- Add 'used' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'used'
    ) THEN
        ALTER TABLE otps ADD COLUMN used BOOLEAN NOT NULL DEFAULT FALSE;
        RAISE NOTICE '✅ Added column: used';
    END IF;

    -- Add 'attempts' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'attempts'
    ) THEN
        ALTER TABLE otps ADD COLUMN attempts INTEGER NOT NULL DEFAULT 0;
        RAISE NOTICE '✅ Added column: attempts';
    END IF;

    -- Add 'created_at' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'created_at'
    ) THEN
        ALTER TABLE otps ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();
        RAISE NOTICE '✅ Added column: created_at';
    END IF;

    -- Add 'expires_at' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'expires_at'
    ) THEN
        ALTER TABLE otps ADD COLUMN expires_at TIMESTAMP WITH TIME ZONE NOT NULL;
        RAISE NOTICE '✅ Added column: expires_at';
    END IF;
END $$;

-- Step 3: Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_otp_phone ON otps(phone);
CREATE INDEX IF NOT EXISTS idx_otp_expires_at ON otps(expires_at);

-- Step 4: Verify table structure
SELECT '✅ Final table structure:' as info;
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns 
WHERE table_name = 'otps'
ORDER BY ordinal_position;

-- Step 5: Test insert
INSERT INTO otps (phone, code, created_at, expires_at, used, attempts)
VALUES (
    '+919876543210',
    '123456',
    NOW(),
    NOW() + INTERVAL '10 minutes',
    false,
    0
);

-- Step 6: Verify insert worked
SELECT '✅ Test OTP inserted:' as info;
SELECT * FROM otps WHERE phone = '+919876543210';

-- Step 7: Cleanup test data
DELETE FROM otps WHERE phone = '+919876543210';

SELECT '✅ OTP table is ready! All columns exist.' as result;

