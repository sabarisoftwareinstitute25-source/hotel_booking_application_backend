@echo off
echo ========================================
echo Hotel Booking App - Setup Check
echo ========================================
echo.

echo [1/5] Checking PostgreSQL service...
sc query postgresql-x64-* >nul 2>&1
if %errorlevel% equ 0 (
    echo    [OK] PostgreSQL service found
) else (
    echo    [FAIL] PostgreSQL service not found
    echo    → Start PostgreSQL from Services (services.msc)
)
echo.

echo [2/5] Checking database...
psql -U postgres -lqt 2>nul | findstr "hotelbooking-app" >nul
if %errorlevel% equ 0 (
    echo    [OK] Database 'hotelbooking-app' exists
) else (
    echo    [FAIL] Database 'hotelbooking-app' does NOT exist
    echo    → Run: psql -U postgres -c "CREATE DATABASE hotelbooking-app;"
)
echo.

echo [3/5] Checking port 8080...
netstat -an | findstr ":8080" >nul
if %errorlevel% equ 0 (
    echo    [OK] Port 8080 is in use (backend may be running)
) else (
    echo    [INFO] Port 8080 is not in use (backend not running)
    echo    → Start backend with: mvnw.cmd spring-boot:run
)
echo.

echo [4/5] Checking Java...
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo    [OK] Java is installed
    java -version 2>&1 | findstr "version"
) else (
    echo    [FAIL] Java is NOT installed or not in PATH
    echo    → Install Java 17 or higher
)
echo.

echo [5/5] Checking Maven wrapper...
if exist mvnw.cmd (
    echo    [OK] Maven wrapper found
) else (
    echo    [FAIL] Maven wrapper not found
    echo    → Ensure you're in mobileapp/mobileapp directory
)
echo.

echo ========================================
echo Summary
echo ========================================
echo.
echo Next steps:
echo 1. Fix any issues above
echo 2. Start backend: mvnw.cmd spring-boot:run
echo 3. Run Flutter app: flutter run
echo.
echo For detailed help, see:
echo - TROUBLESHOOT.md
echo - FIX_COMMON_ISSUES.md
echo.
pause

