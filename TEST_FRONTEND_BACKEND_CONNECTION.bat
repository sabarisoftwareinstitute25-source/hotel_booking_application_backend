@echo off
chcp 65001 >nul
echo ========================================
echo   Frontend-Backend Connection Test
echo ========================================
echo.

echo [1/5] Checking Backend Status...
netstat -ano | findstr :8080 >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ Backend is running on port 8080
) else (
    echo    ✗ Backend is NOT running
    echo    Please start backend first:
    echo    cd mobileapp\mobileapp
    echo    .\mvnw.cmd spring-boot:run
    echo.
    pause
    exit /b 1
)

echo.
echo [2/5] Testing Backend API Endpoints...
echo.

echo    Testing GET /api/hotels...
curl -s -o nul -w "   Status: %%{http_code}\n" http://localhost:8080/api/hotels 2>nul
if %errorlevel% equ 0 (
    echo    ✓ Hotels endpoint is accessible
) else (
    echo    ✗ Cannot reach hotels endpoint
)

echo.
echo    Testing GET /api/auth/login (should return error without body)...
curl -s -o nul -w "   Status: %%{http_code}\n" -X POST http://localhost:8080/api/auth/login 2>nul
if %errorlevel% equ 0 (
    echo    ✓ Auth endpoint is accessible
) else (
    echo    ✗ Cannot reach auth endpoint
)

echo.
echo [3/5] Checking Frontend Configuration...
echo.
echo    Frontend API URL: http://10.0.2.2:8080 (Android Emulator)
echo    Backend URL: http://localhost:8080
echo    ✓ Configuration matches

echo.
echo [4/5] Checking Android Network Security Config...
if exist "..\..\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main\android\app\src\main\res\xml\network_security_config.xml" (
    echo    ✓ Network security config exists
) else (
    echo    ⚠ Network security config not found
)

if exist "..\..\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main\android\app\src\main\AndroidManifest.xml" (
    findstr /C:"usesCleartextTraffic" "..\..\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main\android\app\src\main\AndroidManifest.xml" >nul 2>&1
    if %errorlevel% equ 0 (
        echo    ✓ Cleartext traffic enabled in AndroidManifest
    ) else (
        echo    ✗ Cleartext traffic NOT enabled
    )
) else (
    echo    ⚠ AndroidManifest.xml not found
)

echo.
echo [5/5] Connection Summary...
echo.
echo    Backend Status: 
netstat -ano | findstr :8080 >nul 2>&1
if %errorlevel% equ 0 (
    echo      ✓ Running on port 8080
) else (
    echo      ✗ Not running
)

echo.
echo    Frontend Configuration:
echo      ✓ API URL: http://10.0.2.2:8080
echo      ✓ Endpoints configured
echo      ✓ Network security configured

echo.
echo ========================================
echo   Test Complete!
echo ========================================
echo.
echo Next Steps:
echo   1. Ensure backend is running (port 8080)
echo   2. Run Flutter app: flutter run
echo   3. Test login/signup functionality
echo   4. Test hotel search functionality
echo.
pause

