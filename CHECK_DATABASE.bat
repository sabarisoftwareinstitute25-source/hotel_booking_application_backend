@echo off
REM Avoid UTF-8 codepage / unicode symbols in batch output (can break parsing on some Windows setups)
REM chcp 65001 >nul
echo ========================================
echo   Database Connection Check
echo ========================================
echo.

echo [1/5] Checking PostgreSQL Service...
REM Use NET START (stable in batch) to detect any running PostgreSQL Windows service.
REM (Avoid PowerShell/parentheses parsing issues inside .bat files.)
net start | findstr /I /C:"PostgreSQL" >nul 2>&1
if %errorlevel% equ 0 (
    echo    OK  PostgreSQL service is running
) else (
    echo    FAIL PostgreSQL service NOT running
    echo    Please start PostgreSQL service ^(Services app^) and re-run this script.
    echo    Tip: If installed, service name is often like "postgresql-x64-17"
    pause
    exit /b 1
)

echo.
echo [2/5] Testing Database Connection...
REM Locate psql (not always on PATH on Windows)
set "PSQL_EXE=psql"
if exist "C:\Program Files\PostgreSQL\17\bin\psql.exe" set "PSQL_EXE=C:\Program Files\PostgreSQL\17\bin\psql.exe"
"%PSQL_EXE%" -U postgres -c "SELECT version();" >nul 2>&1
if %errorlevel% equ 0 (
    echo    OK  PostgreSQL connection successful
) else (
    echo    FAIL Cannot connect to PostgreSQL
    echo    Please check PostgreSQL is running and credentials are correct.
    echo    If you see a password prompt, set PGPASSWORD env var or use pgAdmin to create the DB.
    pause
    exit /b 1
)

echo.
echo [3/5] Checking Database Existence...
"%PSQL_EXE%" -U postgres -l -t 2>nul | findstr /C:"hotelbooking-app" >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ Database 'hotelbooking-app' exists
) else (
    echo    ⚠ Database 'hotelbooking-app' does NOT exist
    echo    Creating database...
    "%PSQL_EXE%" -U postgres -c "CREATE DATABASE \"hotelbooking-app\";" 2>nul
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
echo [4/5] Checking Database Tables...
"%PSQL_EXE%" -U postgres -d hotelbooking-app -c "\dt" 2>nul | findstr /C:"users\|hotels\|bookings\|rooms\|otps" >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ Tables exist (will be created/updated by Hibernate)
) else (
    echo    ⚠ Tables not found (will be created by Hibernate on startup)
)

echo.
echo [5/5] Database Configuration Summary...
echo    Database Name: hotelbooking-app
echo    Username: postgres
echo    Password: postgres
echo    Port: 5432
echo    Host: localhost
echo    DDL Mode: update (auto-create/update tables)
echo.

echo ========================================
echo   Database Check Complete!
echo ========================================
echo.
echo Next Steps:
echo   1. Start backend: cd mobileapp\mobileapp && .\mvnw.cmd spring-boot:run
echo   2. Hibernate will create/update tables automatically
echo   3. Check backend logs for table creation messages
echo.
pause

