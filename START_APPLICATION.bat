@echo off
setlocal EnableExtensions EnableDelayedExpansion
REM Avoid changing code page (can break batch parsing on some setups)
REM chcp 65001 >nul
title Hotel Booking App - Backend Server
color 0A

echo ========================================
echo   Hotel Booking Application
echo   Backend Server Startup
echo ========================================
echo.

echo [1/4] Checking PostgreSQL Service...
REM Previous wildcard sc query is unreliable on Windows. Use NET START.
net start | findstr /I /C:"PostgreSQL" >nul 2>&1
if %errorlevel% equ 0 (
    echo    OK  PostgreSQL is running
) else (
    echo    FAIL PostgreSQL NOT running
    echo.
    echo    Please start PostgreSQL:
    echo    1. Open Services ^(services.msc^)
    echo    2. Find postgresql-x64-XX
    echo    3. Right-click → Start
    echo.
    pause
    exit /b 1
)

echo.
echo [2/4] Checking Database...
REM Locate psql.exe if not in PATH
set "PSQL_EXE=psql"
if exist "C:\Program Files\PostgreSQL\17\bin\psql.exe" set "PSQL_EXE=C:\Program Files\PostgreSQL\17\bin\psql.exe"
"%PSQL_EXE%" -U postgres -l -t 2>nul | findstr /C:"hotelbooking-app" >nul 2>&1
if %errorlevel% equ 0 (
    echo    ✓ Database 'hotelbooking-app' exists
) else (
    echo    ⚠ Creating database 'hotelbooking-app'...
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
echo [3/4] Backend Configuration...
echo    Port: 8080
echo    Database: hotelbooking-app
echo    URL: http://localhost:8080
echo    CORS: Enabled
echo    Security: All endpoints accessible

echo.
echo [3.5/4] Ensuring port 8080 is free...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080" ^| findstr "LISTENING"') do (
    echo    Port 8080 is in use. Stopping PID %%a ...
    taskkill /PID %%a /F >nul 2>&1
)

echo.
echo [4/4] Starting Backend Server...
echo.
echo    ════════════════════════════════════════
echo    Backend will start on: http://localhost:8080
echo    ════════════════════════════════════════
echo.
echo    Wait for: "Started MobileappApplication"
echo    Then start Flutter app in another terminal
echo.
echo    Press Ctrl+C to stop the server
echo.

cd mobileapp
REM Ensure JAVA_HOME is set (required by mvnw.cmd). Prefer JDK 21 for this project.
if "!JAVA_HOME!"=="" (
    echo    JAVA_HOME is not set. Trying to auto-detect JDK...
) else (
    if not exist "!JAVA_HOME!\bin\java.exe" (
        echo    JAVA_HOME is set but invalid: !JAVA_HOME!
        echo    Trying to auto-detect JDK...
        set "JAVA_HOME="
    )
)
if "!JAVA_HOME!"=="" (
    if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
        set "JAVA_HOME=C:\Program Files\Java\jdk-21"
        set "PATH=!JAVA_HOME!\bin;!PATH!"
        echo    Using JAVA_HOME: !JAVA_HOME!
    )
)
if "!JAVA_HOME!"=="" (
    echo    ERROR: JAVA_HOME is not set to a valid JDK. Please set it to a JDK folder (example: C:\Program Files\Java\jdk-21)
    pause
    exit /b 1
)
call mvnw.cmd spring-boot:run

pause

