@echo off
chcp 65001 >nul
echo ========================================
echo   Complete Application Fix & Test
echo ========================================
echo.

echo [1/6] Checking PostgreSQL...
sc query postgresql-x64-* >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ PostgreSQL is running
) else (
    echo    ✗ PostgreSQL NOT running
    echo    Please start PostgreSQL service
    pause
    exit /b 1
)

echo.
echo [2/6] Checking Database...
psql -U postgres -l -t 2>nul | findstr /C:"hotelbooking-app" >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ Database 'hotelbooking-app' exists
) else (
    echo    ⚠ Creating database 'hotelbooking-app'...
    psql -U postgres -c "CREATE DATABASE \"hotelbooking-app\";" 2>nul
    if %errorlevel% equ 0 (
        echo    ✓ Database created
    ) else (
        echo    ✗ Failed to create database
        pause
        exit /b 1
    )
)

echo.
echo [3/6] Checking Backend Code...
cd mobileapp
if exist "target\classes\com\hotelbooking\mobileapp\MobileappApplication.class" (
    echo    ✓ Backend compiled
) else (
    echo    ⚠ Backend not compiled (will compile on startup)
)

echo.
echo [4/6] Checking Frontend Configuration...
if exist "..\..\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main\lib\config\api_config.dart" (
    echo    ✓ Frontend API config exists
) else (
    echo    ✗ Frontend API config missing
)

echo.
echo [5/6] Application Configuration Summary...
echo    Backend Port: 8080
echo    Database: hotelbooking-app
echo    Frontend URL: http://10.0.2.2:8080
echo    CORS: Enabled for all origins
echo    Security: All endpoints accessible
echo    DDL Mode: update (auto-create tables)

echo.
echo [6/6] Starting Backend...
echo    This will:
echo    1. Compile the backend
echo    2. Connect to database
echo    3. Create/update tables automatically
echo    4. Start API server on port 8080
echo.
echo    Wait for: "Started MobileappApplication"
echo.

cd mobileapp
call mvnw.cmd spring-boot:run

pause

