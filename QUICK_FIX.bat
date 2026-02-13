@echo off
echo ========================================
echo   QUICK FIX FOR ERRORS
echo ========================================
echo.

echo Fixing Frontend (Flutter)...
cd /d "D:\flutter\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main"
flutter clean
flutter pub get
echo.

echo Fixing Backend (Java)...
cd /d "C:\Users\hp\Downloads\mobileapp\mobileapp\mobileapp"
call mvn clean compile
echo.

echo ========================================
echo   FIX COMPLETE
echo ========================================
echo.
echo If errors persist, please share:
echo   1. Exact error message
echo   2. Frontend or Backend?
echo   3. When does it occur?
echo.
pause
