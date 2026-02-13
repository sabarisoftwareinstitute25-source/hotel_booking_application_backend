# Vendor ID Format and Password Security Update

## Changes Made

### 1. Vendor ID Format Changed
- **Old Format**: `VRyyyyMM0001` (e.g., `VR2026010001`)
- **New Format**: `EIH-yyyy-V-mm-0001` (e.g., `EIH-2026-V-01-0001`)

**Format Breakdown:**
- `EIH` - Prefix (Enterprise Identity Hub)
- `yyyy` - 4-digit year (e.g., 2026)
- `V` - Vendor identifier
- `mm` - 2-digit month (e.g., 01 for January)
- `0001` - 4-digit sequential number (resets monthly)

**Examples:**
- First vendor in January 2026: `EIH-2026-V-01-0001`
- Second vendor in January 2026: `EIH-2026-V-01-0002`
- First vendor in February 2026: `EIH-2026-V-02-0001`

### 2. Password Hashing Implemented
- **Before**: Passwords stored as plain text in database
- **After**: Passwords hashed using BCrypt before storage

**Security Features:**
- Passwords are hashed using BCrypt (one-way encryption)
- Original passwords cannot be retrieved from database
- Each password hash is unique (even for same password)
- Password field is excluded from API responses

## Implementation Details

### Vendor ID Generation
**File**: `IdGeneratorService.java`
- Method: `generateVendorRegistrationId()`
- Uses database lookup to find highest sequence number for current month
- Automatically increments sequence number
- Resets sequence counter monthly

### Password Hashing
**File**: `PasswordEncoderConfig.java` (NEW)
- Configures BCryptPasswordEncoder as Spring Bean
- Uses BCrypt with default strength (10 rounds)

**File**: `VendorService.java`
- All password assignments now use `passwordEncoder.encode()`
- Passwords are hashed before saving to database
- Password field is excluded from response objects

## Database Impact

### Password Storage
- **Before**: `password` column contained plain text
- **After**: `password` column contains BCrypt hash (60 characters)
  - Example: `$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy`

### Vendor ID Format
- Existing vendors with old format (`VRyyyyMM0001`) will remain unchanged
- New vendors will use new format (`EIH-yyyy-V-mm-0001`)

## API Response Changes

### Account Details Response
**Before:**
```json
{
  "success": true,
  "message": "Account details saved successfully",
  "vendorId": "VR2026010001",
  "vendor": {
    "vendorId": "VR2026010001",
    "fullName": "John Doe",
    "businessName": "ABC Hotels",
    "phone": "9876543210",
    "email": "john@example.com",
    "password": "plaintext123"  // ❌ Exposed!
  }
}
```

**After:**
```json
{
  "success": true,
  "message": "Account details saved successfully",
  "vendorId": "EIH-2026-V-01-0001",
  "vendor": {
    "vendorId": "EIH-2026-V-01-0001",
    "fullName": "John Doe",
    "businessName": "ABC Hotels",
    "phone": "9876543210",
    "email": "john@example.com"
    // ✅ Password field excluded
  }
}
```

## Testing

### Test Vendor ID Generation
1. Create a new vendor account
2. Check the `vendorId` in response
3. Should match format: `EIH-yyyy-V-mm-0001`
4. Verify in database: `SELECT vendor_id FROM vendors ORDER BY created_at DESC LIMIT 1;`

### Test Password Hashing
1. Create a vendor with password: `test123`
2. Check database: `SELECT password FROM vendors WHERE vendor_id = 'EIH-2026-V-01-0001';`
3. Should see BCrypt hash (starts with `$2a$10$`)
4. Should NOT see plain text `test123`
5. Check API response - password field should be null/absent

### Test Password Verification (Future)
To verify passwords during login, use:
```java
passwordEncoder.matches(plainPassword, hashedPassword)
```

## Migration Notes

### Existing Data
- Existing vendors with old ID format will continue to work
- Existing plain text passwords should be migrated to hashed format
- Consider adding a migration script to hash existing passwords

### New Registrations
- All new vendor registrations will:
  - Use new ID format: `EIH-yyyy-V-mm-0001`
  - Store hashed passwords
  - Exclude password from API responses

## Security Benefits

1. **Password Protection**: Even if database is compromised, passwords cannot be retrieved
2. **API Security**: Passwords never exposed in API responses
3. **Industry Standard**: BCrypt is widely used and secure
4. **Unique Hashes**: Same password produces different hashes (salt included)

## Files Modified

1. `IdGeneratorService.java` - Updated vendor ID generation
2. `VendorService.java` - Added password hashing, excluded password from responses
3. `PasswordEncoderConfig.java` - NEW - BCrypt configuration
4. `Vendor.java` - Updated comment for password field

## Next Steps

1. **Password Migration**: Create script to hash existing plain text passwords
2. **Login Implementation**: Add password verification for vendor login
3. **Password Reset**: Implement secure password reset flow
4. **Testing**: Test with multiple vendors to verify ID sequence

