@echo off
chcp 65001 >nul
echo ========================================
echo   Complete Setup and Connection Test
echo ========================================
echo.

echo [Step 1/5] Checking PostgreSQL...
sc query postgresql-x64-* >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ PostgreSQL service found
) else (
    echo    ✗ PostgreSQL service not found
    echo    Please install and start PostgreSQL
    pause
    exit /b 1
)

echo.
echo [Step 2/5] Checking database...
psql -U postgres -l -t 2>nul | findstr /C:"hotelbooking-app" >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ Database 'hotelbooking-app' exists
) else (
    echo    ⚠ Creating database 'hotelbooking-app'...
    psql -U postgres -c "CREATE DATABASE \"hotelbooking-app\";" 2>nul
    if %errorlevel% equ 0 (
        echo    ✓ Database created successfully
    ) else (
        echo    ✗ Failed to create database
        echo    Please create manually: psql -U postgres -c "CREATE DATABASE \"hotelbooking-app\";"
        pause
        exit /b 1
    )
)

echo.
echo [Step 3/5] Backend Configuration:
echo    Port: 8080
echo    Database: hotelbooking-app
echo    Frontend URL (Emulator): http://10.0.2.2:8080
echo    Frontend URL (Physical): http://YOUR_IP:8080
echo.
echo [Step 4/5] Starting Backend...
echo    This will start Spring Boot server
echo    Wait for: "Started MobileappApplication"
echo    Then test: http://localhost:8080/api/hotels
echo.
echo [Step 5/5] After backend starts:
echo    1. Open new terminal
echo    2. cd hotel_booking_mobile_application-main\hotel_booking_mobile_application-main
echo    3. flutter run
echo.
echo ========================================
echo.

cd mobileapp
call mvnw.cmd spring-boot:run

pause

