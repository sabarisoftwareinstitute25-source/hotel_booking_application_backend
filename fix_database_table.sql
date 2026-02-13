-- Fix database table name from 'users' to 'usersaccount'
-- Run this script to migrate existing data

-- Step 1: Create new table if it doesn't exist
CREATE TABLE IF NOT EXISTS usersaccount (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    profile_image VARCHAR(500),
    password VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Step 2: Migrate data from old table to new table (if old table exists)
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'users') THEN
        INSERT INTO usersaccount (id, name, email, phone, profile_image, password, created_at)
        SELECT id, name, email, phone, profile_image, password, created_at
        FROM users
        ON CONFLICT (id) DO NOTHING;
        
        RAISE NOTICE 'Data migrated from users to usersaccount';
    END IF;
END $$;

-- Step 3: Update foreign key references in bookings table
-- Check if user_id column exists and update constraint if needed
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'bookings' AND column_name = 'user_id'
    ) THEN
        -- Drop old foreign key constraint if it exists
        ALTER TABLE bookings DROP CONSTRAINT IF EXISTS fk_booking_user;
        
        -- Add new foreign key constraint pointing to usersaccount
        ALTER TABLE bookings 
        ADD CONSTRAINT fk_booking_user 
        FOREIGN KEY (user_id) REFERENCES usersaccount(id);
        
        RAISE NOTICE 'Foreign key constraint updated';
    END IF;
END $$;

-- Step 4: Verify tables
SELECT 'usersaccount table:' as info;
SELECT COUNT(*) as row_count FROM usersaccount;

SELECT 'bookings table:' as info;
SELECT COUNT(*) as row_count FROM bookings;

-- Step 5: (Optional) Drop old table after verification
-- Uncomment the line below ONLY after verifying data migration
-- DROP TABLE IF EXISTS users CASCADE;

