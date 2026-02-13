# Hotel Booking Mobile Application - Backend

## Quick Start

### Prerequisites
- Java 11 or higher
- PostgreSQL 12 or higher
- Maven 3.6+

### Database Setup
1. Create database: `CREATE DATABASE hotelbooking-app;`
2. Run SQL scripts in order:
   - `create_vendors_table_simple.sql`
   - `create_hotel_vendors_table.sql`
   - `add_vendor_id_to_hotel_vendors.sql`

### Run Application
```bash
./mvnw spring-boot:run
```

Server will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/signup` - User signup
- `POST /api/auth/complete-signup` - Complete signup with OTP
- `POST /api/auth/send-verification-code` - Send OTP
- `POST /api/auth/verify-phone-otp` - Verify OTP

### Vendor Registration
- `POST /api/hotels/vendor/account-details` - Save account details
- `POST /api/hotels/vendor/register` - Complete vendor registration
- `GET /api/hotels/vendor/profile` - Get vendor profile
- `GET /api/hotels/vendor/validation-rules` - Get validation rules

### Hotels
- `GET /api/hotels` - Get all hotels
- `GET /api/hotels/search` - Search hotels
- `GET /api/hotels/{id}` - Get hotel by ID
- `GET /api/hotels/rooms` - Get hotel rooms

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/user/{userId}` - Get user bookings
- `POST /api/bookings/cancel` - Cancel booking

### Payments
- `POST /api/payments/process` - Process payment
- `GET /api/payments/status` - Get payment status

## ID Formats

- **User Account ID:** `EIHyyyyCmm000001` (e.g., `EIH2026C01000001`)
- **Vendor ID:** `EIHyyyyVmm0001` (e.g., `EIH2026V010001`)
- **Hotel Registration ID:** Same as Vendor ID if vendor account exists, otherwise `HVyyyyMM0001`

## Security

- Passwords are encrypted using BCrypt
- All passwords stored as hashes (never plain text)
- CORS enabled for mobile apps

## Database Configuration

See `src/main/resources/application.properties` for database connection settings.

## Documentation

- `VALIDATION_RULES.md` - Validation rules and requirements
- `VERIFICATION_CHECKLIST.md` - Verification checklist
- `VENDOR_ID_AND_PASSWORD_UPDATE.md` - Recent updates
- `USER_ACCOUNT_ID_AND_PASSWORD_UPDATE.md` - User account updates
- `VENDOR_ID_AS_REGISTRATION_ID.md` - Vendor ID linking
- `DEBUG_REGISTRATION_ISSUE.md` - Debugging guide

