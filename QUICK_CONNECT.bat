@echo off
chcp 65001 >nul
echo ========================================
echo   Frontend-Backend Connection Setup
echo ========================================
echo.

echo [1/4] Checking PostgreSQL...
sc query postgresql-x64-* >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ PostgreSQL service found
) else (
    echo    ✗ PostgreSQL service not found
    echo    Please ensure PostgreSQL is installed and running
    pause
    exit /b 1
)

echo.
echo [2/4] Checking database...
psql -U postgres -l -t | findstr /C:"hotelbooking-app" >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ Database 'hotelbooking-app' exists
) else (
    echo    ⚠ Database 'hotelbooking-app' not found
    echo    Creating database...
    psql -U postgres -c "CREATE DATABASE \"hotelbooking-app\";" 2>nul
    if %errorlevel% equ 0 (
        echo    ✓ Database created successfully
    ) else (
        echo    ✗ Failed to create database
        echo    Please create manually: psql -U postgres -c "CREATE DATABASE \"hotelbooking-app\";"
    )
)

echo.
echo [3/4] Backend Configuration:
echo    Port: 8080
echo    Database: hotelbooking-app
echo    Frontend URL (Emulator): http://10.0.2.2:8080
echo    Frontend URL (Physical): http://YOUR_IP:8080

echo.
echo [4/4] Starting Backend...
echo    This will start the Spring Boot server
echo    Press Ctrl+C to stop
echo.
echo ========================================
echo.

cd mobileapp
call mvnw.cmd spring-boot:run

pause

