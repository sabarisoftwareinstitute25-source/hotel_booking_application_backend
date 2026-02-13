# Vendor ID as Registration ID

## Change Summary

When a vendor completes hotel registration, the system now uses their **existing vendor ID** (`EIH2026V010004`) as the **registration ID** instead of generating a new one (`HV2026010001`).

## How It Works

### Scenario 1: Vendor Has Account Details (Recommended Flow)

1. **Step 1: Register Account Details**
   - Vendor enters: Full Name, Business Name, Phone/Email, Password
   - System creates vendor account with ID: `EIH2026V010004`
   - Stored in `vendors` table

2. **Step 2: Complete Hotel Registration**
   - Vendor completes full registration form (all 5 steps)
   - System searches for vendor by email/phone
   - **Finds vendor with ID: `EIH2026V010004`**
   - **Uses vendor ID as registration ID: `EIH2026V010004`**
   - Creates hotel registration with **same ID**: `EIH2026V010004`
   - Links them: `hotel_vendors.registration_id = 'EIH2026V010004'` and `hotel_vendors.vendor_id = 'EIH2026V010004'`

### Scenario 2: Vendor Completes Registration Without Account Details (Fallback)

1. **Direct Registration (No Account Details)**
   - Vendor completes full registration form directly
   - System searches for vendor by email/phone
   - **No vendor account found**
   - System generates new registration ID: `HV2026010001` (based on property type)
   - Creates hotel registration with ID: `HV2026010001`
   - `vendor_id` is `null` (no account link)

## Benefits

1. **Consistent ID**: Vendor uses the same ID throughout the system
2. **Simpler Tracking**: One ID to track vendor account and hotel registration
3. **Better Data Integrity**: Clear link between account and registration
4. **Easier Queries**: Can find hotel registration directly using vendor ID

## Database Structure

### `vendors` Table
```
vendor_id: EIH2026V010004 (Primary Key)
full_name: John Doe
business_name: ABC Hotels
email: john@example.com
phone: +919876543210
```

### `hotel_vendors` Table
```
registration_id: EIH2026V010004 (Primary Key) ← Same as vendor_id
vendor_id: EIH2026V010004 (Foreign Key to vendors table)
hotel_id: H202601150001
hotel_name: ABC Hotel
...
```

## Code Changes

**`HotelService.java` - `registerVendor()` method:**

```java
// Find existing vendor
Optional<Vendor> existingVendor = vendorRepository.findByEmail(email);

if (existingVendor.isPresent()) {
    vendorId = existingVendor.get().getVendorId();
    // Use vendor ID as registration ID
    registrationId = vendorId;  // ← Same ID!
} else {
    // Generate new ID only if no vendor account exists
    registrationId = idGeneratorService.generateHotelVendorRegistrationId(propertyType);
}

vendor.setRegistrationId(registrationId);
vendor.setVendorId(vendorId);
```

## Query Examples

### Find Hotel Registration by Vendor ID
```sql
SELECT * FROM hotel_vendors 
WHERE registration_id = 'EIH2026V010004';
-- or
WHERE vendor_id = 'EIH2026V010004';
```

### Get Vendor Account and Hotel Registration Together
```sql
SELECT 
    v.vendor_id,
    v.full_name,
    v.business_name,
    hv.registration_id,
    hv.hotel_name,
    hv.hotel_id
FROM vendors v
INNER JOIN hotel_vendors hv ON v.vendor_id = hv.vendor_id
WHERE v.vendor_id = 'EIH2026V010004';
```

## Migration Notes

- **Existing Data**: If you have existing hotel registrations with different IDs, you may need to:
  1. Find the corresponding vendor account
  2. Update `hotel_vendors.registration_id` to match `vendor_id`
  3. Update `hotel_vendors.vendor_id` to link them

- **New Registrations**: All new registrations will automatically use vendor ID as registration ID if vendor account exists.

## Testing

1. **Test with Account Details First:**
   - Register account details → Get vendor ID `EIH2026V010004`
   - Complete hotel registration → Should get registration ID `EIH2026V010004` (same!)

2. **Test Direct Registration:**
   - Complete hotel registration without account details → Should get registration ID `HV2026010001` (new ID)

3. **Verify Database:**
   ```sql
   SELECT registration_id, vendor_id, hotel_name 
   FROM hotel_vendors 
   WHERE registration_id = 'EIH2026V010004';
   ```
   Both `registration_id` and `vendor_id` should be `EIH2026V010004`.

