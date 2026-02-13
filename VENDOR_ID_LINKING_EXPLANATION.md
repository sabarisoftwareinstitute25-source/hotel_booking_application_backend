# Vendor ID Linking Explanation

## Problem
When a vendor completes their profile, they get two different IDs:
- **Vendor Account ID**: `EIH2026V010004` (from `vendors` table)
- **Hotel Registration ID**: `HV2026010001` (from `hotel_vendors` table)

## Why Two IDs?

These are **two separate IDs for two different purposes**:

1. **`EIH2026V010004` (Vendor Account ID)**
   - Created when vendor saves **Account Details** (Full Name, Business Name, Phone/Email, Password)
   - Stored in `vendors` table
   - Format: `EIH` + `yyyy` + `V` + `mm` + `0001`
   - Example: `EIH2026V010004` = EIH + 2026 + V + 01 + 0004

2. **`HV2026010001` (Hotel Registration ID)**
   - Created when vendor completes **Full Registration Form** (all 5 steps)
   - Stored in `hotel_vendors` table
   - Format: `HV` + `yyyyMM` + `0001` (for Hotel)
   - Example: `HV2026010001` = HV + 202601 + 0001

## Solution: Linking the IDs

To link these two IDs together, we've added:

### 1. Database Schema Update
- Added `vendor_id` column to `hotel_vendors` table
- This column stores the vendor account ID (`EIH2026V010004`)
- Links the hotel registration to the vendor account

### 2. Code Changes

**`HotelVendor.java`**
- Added `vendorId` field to store the vendor account ID
- Added getter/setter methods

**`HotelService.java`**
- When registering a hotel vendor, the system now:
  1. Searches for existing vendor by email or phone number
  2. If found, links the hotel registration to the vendor account
  3. Sets `vendorId` in `HotelVendor` entity

### 3. Database Migration

Run the SQL script to add the column:
```sql
ALTER TABLE hotel_vendors
ADD COLUMN IF NOT EXISTS vendor_id VARCHAR(50);

CREATE INDEX IF NOT EXISTS idx_hotel_vendors_vendor_id ON hotel_vendors(vendor_id);
```

## How It Works Now

1. **Step 1: Account Details**
   - Vendor enters: Full Name, Business Name, Phone/Email, Password
   - System creates vendor account with ID: `EIH2026V010004`
   - Stored in `vendors` table

2. **Step 2: Complete Registration**
   - Vendor completes full registration form (all 5 steps)
   - System searches for vendor by email/phone
   - Finds vendor with ID: `EIH2026V010004`
   - Creates hotel registration with ID: `HV2026010001`
   - Links them: `hotel_vendors.vendor_id = 'EIH2026V010004'`

## Result

Now when you query the database:
```sql
SELECT 
    hv.registration_id,  -- HV2026010001
    hv.vendor_id,        -- EIH2026V010004
    v.vendor_id,         -- EIH2026V010004
    v.full_name,
    hv.hotel_name
FROM hotel_vendors hv
LEFT JOIN vendors v ON hv.vendor_id = v.vendor_id
WHERE hv.registration_id = 'HV2026010001';
```

You can see both IDs are linked!

## Benefits

1. **Data Integrity**: Hotel registrations are linked to vendor accounts
2. **User Tracking**: Can track which vendor owns which hotel registration
3. **Profile Management**: Can fetch vendor account details when viewing hotel registration
4. **Account Linking**: Multiple hotel registrations can belong to the same vendor account

## Files Modified

1. `HotelVendor.java` - Added `vendorId` field
2. `HotelService.java` - Added logic to find and link vendor
3. `add_vendor_id_to_hotel_vendors.sql` - Database migration script

