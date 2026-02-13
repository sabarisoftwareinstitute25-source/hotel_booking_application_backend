-- Fix OTP table schema
-- Run this script if the table is missing columns

-- Check if table exists, if not create it
CREATE TABLE IF NOT EXISTS otps (
    id BIGSERIAL PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    code VARCHAR(6) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    attempts INTEGER NOT NULL DEFAULT 0
);

-- Add missing columns if they don't exist
DO $$
BEGIN
    -- Add 'used' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'used'
    ) THEN
        ALTER TABLE otps ADD COLUMN used BOOLEAN NOT NULL DEFAULT FALSE;
        RAISE NOTICE 'Added column: used';
    END IF;

    -- Add 'attempts' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'attempts'
    ) THEN
        ALTER TABLE otps ADD COLUMN attempts INTEGER NOT NULL DEFAULT 0;
        RAISE NOTICE 'Added column: attempts';
    END IF;

    -- Add 'created_at' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'created_at'
    ) THEN
        ALTER TABLE otps ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();
        RAISE NOTICE 'Added column: created_at';
    END IF;

    -- Add 'expires_at' column if missing
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'otps' AND column_name = 'expires_at'
    ) THEN
        ALTER TABLE otps ADD COLUMN expires_at TIMESTAMP WITH TIME ZONE NOT NULL;
        RAISE NOTICE 'Added column: expires_at';
    END IF;
END $$;

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_otp_phone ON otps(phone);
CREATE INDEX IF NOT EXISTS idx_otp_expires_at ON otps(expires_at);

-- Verify table structure
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
SELECT * FROM otps WHERE phone = '+919876543210';

-- Cleanup test data
DELETE FROM otps WHERE phone = '+919876543210';

