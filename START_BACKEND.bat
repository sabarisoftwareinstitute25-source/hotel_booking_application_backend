@echo off
echo ========================================
echo Starting Hotel Booking Backend
echo ========================================
echo.

REM Delegate to PowerShell script for robust port-kill + JAVA_HOME handling.
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0START_BACKEND_SAFE.ps1"

pause

