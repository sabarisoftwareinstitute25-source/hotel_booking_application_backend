# Validation Rules for Account Details

## Overview
Comprehensive validation rules for Account Details form fields: Name, Business Name, Email, Phone Number, and Password.

## Validation Rules

### 1. Full Name
- **Required**: Yes
- **Min Length**: 2 characters
- **Max Length**: 100 characters
- **Pattern**: `^[a-zA-Z\\s.'-]+$`
- **Allowed Characters**: 
  - Letters (a-z, A-Z)
  - Spaces
  - Dots (.)
  - Apostrophes (')
  - Hyphens (-)
- **Examples**:
  - ✅ Valid: "John Doe", "Mary O'Brien", "Jean-Pierre", "Dr. Smith"
  - ❌ Invalid: "J", "John123", "John@Doe", ""

### 2. Business Name
- **Required**: Yes
- **Min Length**: 2 characters
- **Max Length**: 150 characters
- **Pattern**: `^[a-zA-Z0-9\\s.,&'-]+$`
- **Allowed Characters**:
  - Letters (a-z, A-Z)
  - Numbers (0-9)
  - Spaces
  - Commas (,)
  - Dots (.)
  - Ampersands (&)
  - Apostrophes (')
  - Hyphens (-)
- **Examples**:
  - ✅ Valid: "ABC Hotels", "Hotel & Resorts", "123 Hotels Pvt. Ltd."
  - ❌ Invalid: "A", "Hotel@Resort", "Hotel#123", ""

### 3. Email
- **Required**: Yes (if phone is not provided)
- **Format**: Standard email format
- **Pattern**: `^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$`
- **Max Length**: 150 characters
- **Case**: Converted to lowercase automatically
- **Examples**:
  - ✅ Valid: "john@example.com", "user.name@domain.co.uk", "test+tag@example.com"
  - ❌ Invalid: "invalid.email", "@example.com", "user@", "user@domain"

### 4. Phone Number
- **Required**: Yes (if email is not provided)
- **Format**: Indian phone number (10 digits)
- **Pattern**: `^(\\+91)?[6-9]\\d{9}$`
- **Rules**:
  - Must be exactly 10 digits
  - Must start with 6, 7, 8, or 9 (Indian mobile number format)
  - Optional +91 prefix
  - Spaces and dashes are automatically removed
- **Examples**:
  - ✅ Valid: "9876543210", "+919876543210", "91 98765 43210"
  - ❌ Invalid: "1234567890", "987654321", "98765432101", "1234567890"

### 5. Password
- **Required**: Yes
- **Min Length**: 8 characters
- **Max Length**: 50 characters
- **Pattern**: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$`
- **Requirements**:
  - At least one lowercase letter (a-z)
  - At least one uppercase letter (A-Z)
  - At least one digit (0-9)
  - At least one special character (@$!%*?&)
- **Examples**:
  - ✅ Valid: "Password123!", "MyP@ssw0rd", "Secure#Pass1"
  - ❌ Invalid: "password", "PASSWORD123", "Password123", "Pass1", ""

## Validation Implementation

### Backend Validation

#### 1. DTO Level (AccountDetailsRequest.java)
- Uses Jakarta Bean Validation annotations:
  - `@NotBlank` - Ensures field is not null or empty
  - `@Size` - Validates min/max length
  - `@Pattern` - Validates format using regex

#### 2. Service Level (VendorService.java)
- Uses `ValidationUtils` class for custom validation:
  - `isValidName()` - Validates full name
  - `isValidBusinessName()` - Validates business name
  - `isValidEmail()` - Validates email format
  - `isValidPhone()` - Validates phone number format
  - `isValidPassword()` - Validates password strength
  - `normalizeEmail()` - Normalizes email (lowercase, trim)
  - `normalizePhone()` - Normalizes phone (removes spaces, +91 prefix)

### Error Messages

#### Full Name Errors
- "Full name is required"
- "Full name must be between 2 and 100 characters and contain only letters, spaces, dots, apostrophes, and hyphens"

#### Business Name Errors
- "Business name is required"
- "Business name must be between 2 and 150 characters and contain only letters, numbers, spaces, and common punctuation"

#### Email Errors
- "Phone or email is required"
- "Invalid email format. Please enter a valid email address"

#### Phone Errors
- "Phone or email is required"
- "Invalid phone number format. Please enter a valid 10-digit Indian phone number (e.g., 9876543210)"

#### Password Errors
- "Password is required"
- "Password must be 8-50 characters and contain at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&)"

## API Request Example

```json
{
  "fullName": "John Doe",
  "businessName": "ABC Hotels & Resorts",
  "phoneOrEmail": "john@example.com",
  "password": "SecurePass123!"
}
```

Or with phone:

```json
{
  "fullName": "John Doe",
  "businessName": "ABC Hotels & Resorts",
  "phoneOrEmail": "9876543210",
  "password": "SecurePass123!"
}
```

## Validation Flow

1. **Request Received** → `VendorController.saveAccountDetails()`
2. **Bean Validation** → Jakarta validation annotations check basic constraints
3. **Service Validation** → `VendorService.saveAccountDetails()` performs detailed validation:
   - Validates full name format
   - Validates business name format
   - Determines if phoneOrEmail is email or phone
   - Validates email format (if email)
   - Validates phone format (if phone)
   - Validates password strength
4. **Normalization** → Email/phone normalized before storage
5. **Password Hashing** → Password hashed using BCrypt
6. **Save to Database** → Validated and normalized data saved

## Testing

### Test Valid Data
```json
{
  "fullName": "John Doe",
  "businessName": "ABC Hotels",
  "phoneOrEmail": "john@example.com",
  "password": "SecurePass123!"
}
```
Expected: ✅ Success

### Test Invalid Name
```json
{
  "fullName": "J",
  "businessName": "ABC Hotels",
  "phoneOrEmail": "john@example.com",
  "password": "SecurePass123!"
}
```
Expected: ❌ Error: "Full name must be between 2 and 100 characters..."

### Test Invalid Email
```json
{
  "fullName": "John Doe",
  "businessName": "ABC Hotels",
  "phoneOrEmail": "invalid.email",
  "password": "SecurePass123!"
}
```
Expected: ❌ Error: "Invalid email format..."

### Test Invalid Phone
```json
{
  "fullName": "John Doe",
  "businessName": "ABC Hotels",
  "phoneOrEmail": "1234567890",
  "password": "SecurePass123!"
}
```
Expected: ❌ Error: "Invalid phone number format..."

### Test Weak Password
```json
{
  "fullName": "John Doe",
  "businessName": "ABC Hotels",
  "phoneOrEmail": "john@example.com",
  "password": "password"
}
```
Expected: ❌ Error: "Password must be 8-50 characters and contain..."

## Files Modified

1. **AccountDetailsRequest.java** - Added validation annotations
2. **VendorService.java** - Added detailed validation logic
3. **ValidationUtils.java** - NEW - Utility class for validation methods

