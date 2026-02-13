# User Account ID and Password Encryption Update

## Changes Summary

### 1. User Account ID Format Changed

**Old Format:** `U-yyyy-mm-0001`
- Example: `U-2026-01-0001`

**New Format:** `EIH+yyyy+C+mm+000001`
- Example: `EIH2026C01000001`
- Format breakdown:
  - `EIH` = Prefix
  - `2026` = Year (4 digits)
  - `C` = Constant letter (for Customer/Client)
  - `01` = Month (2 digits)
  - `000001` = Sequence number (6 digits, starting from 000001)

### 2. Password Encryption Added

**Before:** Passwords were stored in **plain text** (security risk!)

**After:** Passwords are now **encrypted using BCrypt** before saving to database.

## Implementation Details

### ID Generation (`IdGeneratorService.java`)

```java
/**
 * Generates a user account ID in format: EIHyyyyCmm000001
 * Format: EIH (prefix) + yyyy (year) + C + mm (month) + 000001 (6-digit sequential number)
 * Example: EIH2026C01000001
 */
public String generateUserId() {
    // Pattern: EIHyyyyCmm000001
    // Finds highest sequence for current year/month
    // Increments and formats as 6-digit number
}
```

**Features:**
- Automatically finds highest sequence number for current month
- Resets sequence counter each month
- 6-digit sequence number (allows up to 999,999 users per month)
- Format: `EIH2026C01000001`, `EIH2026C01000002`, etc.

### Password Encryption (`AuthController.java`)

**Signup Method:**
```java
// Encrypt password before saving
if (request.getPassword() != null && !request.getPassword().isEmpty()) {
    userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
}
```

**Complete Signup Method:**
```java
// Encrypt password before saving
if (password != null && !password.isEmpty()) {
    userAccount.setPassword(passwordEncoder.encode(password));
}
```

**Password Encoder:**
- Uses `BCryptPasswordEncoder` from Spring Security
- Automatically salts passwords
- One-way hashing (cannot be reversed)
- Secure password storage

## Database Impact

### User Account ID Column
- **Column:** `id` in `usersaccount` table
- **Type:** `VARCHAR(20)` (should be increased to `VARCHAR(50)` to accommodate new format)
- **Example:** `EIH2026C01000001`

### Password Column
- **Column:** `password` in `usersaccount` table
- **Type:** `VARCHAR(255)` (BCrypt hashes are 60 characters)
- **Format:** BCrypt hash (e.g., `$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy`)
- **Security:** Encrypted, cannot be read as plain text

## Migration Steps

### 1. Update Database Schema (if needed)

```sql
-- Increase ID column length if needed
ALTER TABLE usersaccount 
ALTER COLUMN id TYPE VARCHAR(50);

-- Verify password column length (should be VARCHAR(255))
SELECT column_name, data_type, character_maximum_length
FROM information_schema.columns
WHERE table_name = 'usersaccount' AND column_name = 'password';
```

### 2. Update Existing User IDs (Optional)

If you have existing users with old format (`U-2026-01-0001`), you can migrate them:

```sql
-- Example migration (adjust based on your data)
UPDATE usersaccount 
SET id = 'EIH2026C01000001'  -- Replace with actual new ID
WHERE id = 'U-2026-01-0001';
```

### 3. Re-encrypt Existing Passwords (Important!)

If you have existing users with plain text passwords, you need to re-encrypt them:

```java
// Run this migration script or update manually
// For each user with plain text password:
String plainPassword = user.getPassword();
String encryptedPassword = passwordEncoder.encode(plainPassword);
user.setPassword(encryptedPassword);
userRepository.save(user);
```

## Testing

### Test 1: User Account Creation
```java
// Create new user account
UserAccount user = new UserAccount();
user.setName("John Doe");
user.setEmail("john@example.com");
user.setPassword("SecurePass123!");

// ID will be auto-generated: EIH2026C01000001
// Password will be encrypted before saving
UserAccount saved = userAccountService.createUserAccount(user);

// Verify
assert saved.getId().startsWith("EIH");
assert saved.getId().matches("^EIH\\d{4}C\\d{2}\\d{6}$");
assert saved.getPassword().startsWith("$2a$"); // BCrypt format
assert !saved.getPassword().equals("SecurePass123!"); // Not plain text
```

### Test 2: Password Verification
```java
// When user logs in
String inputPassword = "SecurePass123!";
String storedHash = user.getPassword();

// Verify password
boolean matches = passwordEncoder.matches(inputPassword, storedHash);
assert matches == true;
```

## Security Benefits

1. **Password Encryption:**
   - Passwords are hashed using BCrypt
   - Cannot be read from database
   - Even database administrators cannot see passwords
   - Protects against data breaches

2. **ID Format:**
   - Consistent format across system
   - Easy to identify user accounts
   - Supports high volume (999,999 users/month)

## Files Modified

1. **`IdGeneratorService.java`**
   - Updated `generateUserId()` method
   - Changed format from `U-yyyy-mm-0001` to `EIHyyyyCmm000001`
   - Updated regex pattern matching

2. **`AuthController.java`**
   - Added `PasswordEncoder` dependency
   - Updated `signup()` method to encrypt passwords
   - Updated `completeSignup()` method to encrypt passwords

## Backward Compatibility

- **Old IDs:** Existing users with `U-yyyy-mm-0001` format will continue to work
- **New IDs:** All new users will get `EIHyyyyCmm000001` format
- **Password Migration:** Existing plain text passwords need to be re-encrypted

## Next Steps

1. ✅ Update ID generation format
2. ✅ Add password encryption
3. ⚠️ Update database schema (if needed)
4. ⚠️ Migrate existing user IDs (optional)
5. ⚠️ Re-encrypt existing passwords (important!)

