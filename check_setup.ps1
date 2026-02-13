# Comprehensive Setup Check Script
# Run this to verify everything is configured correctly

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Hotel Booking App - Setup Check" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$allChecksPassed = $true

# 1. Check PostgreSQL
Write-Host "[1/6] Checking PostgreSQL..." -ForegroundColor Yellow
try {
    $pgService = Get-Service -Name "*postgres*" -ErrorAction SilentlyContinue | Where-Object { $_.Status -eq 'Running' }
    if ($pgService) {
        Write-Host "   ✓ PostgreSQL service is running" -ForegroundColor Green
    } else {
        Write-Host "   ✗ PostgreSQL service is not running" -ForegroundColor Red
        Write-Host "   → Start PostgreSQL service from Services (services.msc)" -ForegroundColor Yellow
        $allChecksPassed = $false
    }
} catch {
    Write-Host "   ⚠ Could not check PostgreSQL service" -ForegroundColor Yellow
    Write-Host "   → Please verify PostgreSQL is installed and running" -ForegroundColor Yellow
}

Write-Host ""

# 2. Check Database Exists
Write-Host "[2/6] Checking database..." -ForegroundColor Yellow
try {
    $env:PGPASSWORD = "postgres"
    $dbCheck = & psql -U postgres -lqt 2>&1 | Select-String "hotelbooking-app"
    if ($dbCheck) {
        Write-Host "   ✓ Database 'hotelbooking-app' exists" -ForegroundColor Green
    } else {
        Write-Host "   ✗ Database 'hotelbooking-app' does not exist" -ForegroundColor Red
        Write-Host "   → Run: psql -U postgres -c 'CREATE DATABASE \"hotelbooking-app\";'" -ForegroundColor Yellow
        $allChecksPassed = $false
    }
    $env:PGPASSWORD = $null
} catch {
    Write-Host "   ⚠ Could not check database (psql not in PATH or PostgreSQL not accessible)" -ForegroundColor Yellow
    Write-Host "   → Manually check: psql -U postgres -l" -ForegroundColor Yellow
}

Write-Host ""

# 3. Check Backend Port
Write-Host "[3/6] Checking backend port 8080..." -ForegroundColor Yellow
$port8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
if ($port8080) {
    Write-Host "   ✓ Port 8080 is in use (backend may be running)" -ForegroundColor Green
} else {
    Write-Host "   ⚠ Port 8080 is not in use (backend not running)" -ForegroundColor Yellow
    Write-Host "   → Start backend with: .\mvnw.cmd spring-boot:run" -ForegroundColor Yellow
}

Write-Host ""

# 4. Test Backend Endpoint
Write-Host "[4/6] Testing backend endpoint..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" `
        -Method POST `
        -Body '{"email":"test","password":"test"}' `
        -ContentType "application/json" `
        -TimeoutSec 5 `
        -ErrorAction Stop
    Write-Host "   ✓ Backend is responding" -ForegroundColor Green
} catch {
    $statusCode = $_.Exception.Response.StatusCode.value__
    if ($statusCode -eq 400 -or $statusCode -eq 401) {
        Write-Host "   ✓ Backend is running (returned expected error)" -ForegroundColor Green
    } elseif ($_.Exception.Message -like "*Unable to connect*" -or $_.Exception.Message -like "*Connection refused*") {
        Write-Host "   ✗ Backend is not running" -ForegroundColor Red
        Write-Host "   → Start backend with: .\mvnw.cmd spring-boot:run" -ForegroundColor Yellow
        $allChecksPassed = $false
    } else {
        Write-Host "   ⚠ Could not connect to backend" -ForegroundColor Yellow
        Write-Host "   → Error: $($_.Exception.Message)" -ForegroundColor Yellow
    }
}

Write-Host ""

# 5. Check Java Installation
Write-Host "[5/6] Checking Java installation..." -ForegroundColor Yellow
try {
    $javaVersion = & java -version 2>&1 | Select-Object -First 1
    if ($javaVersion -like "*version*") {
        Write-Host "   ✓ Java is installed" -ForegroundColor Green
        Write-Host "   → $javaVersion" -ForegroundColor Gray
    } else {
        Write-Host "   ✗ Java is not installed or not in PATH" -ForegroundColor Red
        Write-Host "   → Install Java 17 or higher" -ForegroundColor Yellow
        $allChecksPassed = $false
    }
} catch {
    Write-Host "   ✗ Java is not installed or not in PATH" -ForegroundColor Red
    Write-Host "   → Install Java 17 or higher" -ForegroundColor Yellow
    $allChecksPassed = $false
}

Write-Host ""

# 6. Check Maven Wrapper
Write-Host "[6/6] Checking Maven wrapper..." -ForegroundColor Yellow
if (Test-Path "mvnw.cmd") {
    Write-Host "   ✓ Maven wrapper found" -ForegroundColor Green
} else {
    Write-Host "   ✗ Maven wrapper not found" -ForegroundColor Red
    Write-Host "   → Ensure you're in the correct directory (mobileapp/mobileapp)" -ForegroundColor Yellow
    $allChecksPassed = $false
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Summary" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

if ($allChecksPassed) {
    Write-Host "✓ All critical checks passed!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Yellow
    Write-Host "1. Start backend: .\mvnw.cmd spring-boot:run" -ForegroundColor White
    Write-Host "2. Run Flutter app: flutter run" -ForegroundColor White
    Write-Host "3. Test signup/login in the app" -ForegroundColor White
} else {
    Write-Host "✗ Some checks failed. Please fix the issues above." -ForegroundColor Red
    Write-Host ""
    Write-Host "Common fixes:" -ForegroundColor Yellow
    Write-Host "1. Start PostgreSQL service" -ForegroundColor White
    Write-Host "2. Create database: psql -U postgres -c 'CREATE DATABASE \"hotelbooking-app\";'" -ForegroundColor White
    Write-Host "3. Install Java 17+ if missing" -ForegroundColor White
    Write-Host "4. Start backend: .\mvnw.cmd spring-boot:run" -ForegroundColor White
}

Write-Host ""
Write-Host "For detailed troubleshooting, see: TROUBLESHOOT.md" -ForegroundColor Cyan

