# Debugging Hotel Vendor Registration - Data Not Storing

## Issue
Data is not being stored in the `hotel_vendors` table when submitting the registration form.

## Changes Made

### 1. Enhanced Controller Error Handling (`HotelController.java`)

#### Changed Request Handling
- Changed from `@Valid @RequestBody HotelVendorRegistrationRequest` to `@RequestBody Map<String, Object>`
- Added manual conversion method `convertMapToRequest()` to handle field mapping
- This ensures the frontend Map is properly converted to the DTO

#### Added Comprehensive Logging
- Logs incoming request keys
- Logs converted request fields
- Logs validation errors with details
- Logs database errors with full stack traces
- Logs success with registration ID

#### Improved Error Messages
- More detailed error messages for debugging
- Shows exact field that failed validation
- Shows database constraint violations

### 2. Request Conversion Method

The `convertMapToRequest()` method:
- Handles all field mappings from frontend Map to DTO
- Properly converts types (String, Boolean, List, Map)
- Handles null values gracefully
- Maps all 40+ fields correctly

## Debugging Steps

### Step 1: Check Backend Logs
When you submit the form, check the backend console for:
```
📥 Received vendor registration request
   Request keys: [propertyType, hotelName, ownerName, ...]
✅ Converted to HotelVendorRegistrationRequest
   Hotel Name: ...
   Owner Name: ...
```

### Step 2: Check for Validation Errors
Look for:
```
❌ Validation error: [error message]
```

Common validation errors:
- Missing required fields (hotelName, ownerName, mobileNumber, etc.)
- Invalid format (IFSC code, account number, etc.)
- Invalid Aadhar/GSTIN/FSSAI numbers

### Step 3: Check for Database Errors
Look for:
```
❌ DATABASE CONSTRAINT ERROR saving HotelVendor:
   Error: [error details]
```

Common database errors:
- Unique constraint violations (email, mobile_number already exists)
- NOT NULL constraint violations
- Foreign key violations
- Data type mismatches

### Step 4: Check Transaction Status
Look for:
```
💾 Calling saveAndFlush()...
✅ saveAndFlush() completed - no exceptions thrown
   Saved Registration ID: [ID]
✅ HotelVendor VERIFIED in database!
```

If you see "CRITICAL: HotelVendor was NOT found in database after saveAndFlush!", there's a transaction rollback issue.

## Common Issues and Fixes

### Issue 1: Missing Required Fields
**Symptom**: `IllegalArgumentException: [field] is required`

**Fix**: Ensure all required fields are filled in the form:
- hotelName
- ownerName
- mobileNumber
- addressLine1
- city
- state
- pinCode
- accountHolderName
- bankName
- accountNumber
- ifscCode
- declarationAccepted (must be true)

### Issue 2: Invalid IFSC Code Format
**Symptom**: `IFSC code must be exactly 11 characters in format: AAAA0XXXXXX`

**Fix**: IFSC code must be:
- Exactly 11 characters
- Format: 4 uppercase letters + 0 + 6 alphanumeric
- Example: `HDFC0001234`

### Issue 3: Invalid Account Number Format
**Symptom**: `Account number must be between 9 and 18 characters`

**Fix**: Account number must be:
- Between 9 and 18 characters
- Only letters (A-Z, a-z) and numbers (0-9)
- No special characters or spaces

### Issue 4: Duplicate Email/Mobile
**Symptom**: `Database constraint violation: unique_email` or `unique_mobile_number`

**Fix**: Use a different email or mobile number, or update existing vendor

### Issue 5: Transaction Rollback
**Symptom**: `saveAndFlush()` succeeds but data not found in database

**Fix**: Check for:
- Database connection issues
- Transaction isolation level
- Constraint violations after save
- Check database logs for rollback reasons

## Testing

### Test 1: Submit Form with All Required Fields
1. Fill all 5 steps completely
2. Ensure declaration is accepted
3. Submit form
4. Check backend logs for success message
5. Query database: `SELECT * FROM hotel_vendors;`

### Test 2: Check Backend Logs
```bash
# Watch backend logs while submitting
tail -f logs/application.log
```

Look for:
- Request received
- Conversion successful
- Validation passed
- Database save successful
- Verification successful

### Test 3: Query Database Directly
```sql
-- Check if data exists
SELECT registration_id, hotel_name, owner_name, mobile_number, email, created_at 
FROM hotel_vendors 
ORDER BY created_at DESC 
LIMIT 10;

-- Check for constraint violations
SELECT * FROM hotel_vendors WHERE email = '[test_email]';
SELECT * FROM hotel_vendors WHERE mobile_number = '[test_mobile]';
```

## Next Steps

1. **Submit the form** and check backend console logs
2. **Copy the error message** if any appears
3. **Check database** to see if data was actually saved
4. **Review logs** for detailed error information

## Files Modified

1. `mobileapp/mobileapp/src/main/java/com/hotelbooking/mobileapp/hotel/HotelController.java`
   - Changed request handling to accept Map
   - Added conversion method
   - Enhanced error logging
   - Improved error messages

## Expected Behavior

When form is submitted successfully:
1. Backend receives request ✅
2. Converts Map to DTO ✅
3. Validates all fields ✅
4. Saves to database ✅
5. Returns success response ✅
6. Data visible in `hotel_vendors` table ✅

If any step fails, detailed error will be logged in backend console.

