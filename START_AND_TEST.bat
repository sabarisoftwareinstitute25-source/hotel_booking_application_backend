@echo off
echo ========================================
echo   Start Backend and Test Connection
echo ========================================
echo.

echo Step 1: Starting Spring Boot Backend...
echo.
cd mobileapp
call mvnw.cmd spring-boot:run

