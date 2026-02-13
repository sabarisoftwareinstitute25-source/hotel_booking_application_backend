# PowerShell script to test database and backend connections

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  CONNECTION TEST" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Test PostgreSQL Connection
Write-Host "1. Testing PostgreSQL Connection..." -ForegroundColor Yellow
try {
    $pgTest = psql -U postgres -d hotelbooking-app -c "SELECT version();" 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "   ✅ PostgreSQL connection successful!" -ForegroundColor Green
    } else {
        Write-Host "   ❌ PostgreSQL connection failed!" -ForegroundColor Red
        Write-Host "   Please check:" -ForegroundColor Yellow
        Write-Host "   - PostgreSQL is running" -ForegroundColor Yellow
        Write-Host "   - Database 'hotelbooking-app' exists" -ForegroundColor Yellow
    }
} catch {
    Write-Host "   ❌ PostgreSQL not found or not accessible" -ForegroundColor Red
}

Write-Host ""

# Test Backend API
Write-Host "2. Testing Backend API..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/hotels" -Method GET -TimeoutSec 5 -ErrorAction Stop
    if ($response.StatusCode -eq 200) {
        Write-Host "   ✅ Backend API is running!" -ForegroundColor Green
        Write-Host "   Status Code: $($response.StatusCode)" -ForegroundColor Green
    }
} catch {
    Write-Host "   ❌ Backend API not accessible!" -ForegroundColor Red
    Write-Host "   Please check:" -ForegroundColor Yellow
    Write-Host "   - Backend is running on port 8080" -ForegroundColor Yellow
    Write-Host "   - Run: mvn spring-boot:run" -ForegroundColor Yellow
}

Write-Host ""

# Test Database Tables
Write-Host "3. Checking Database Tables..." -ForegroundColor Yellow
try {
    $tables = psql -U postgres -d hotelbooking-app -c "\dt" 2>&1
    if ($tables -match "users|hotels|bookings|rooms") {
        Write-Host "   ✅ Database tables exist!" -ForegroundColor Green
    } else {
        Write-Host "   ⚠️  Tables may not be created yet" -ForegroundColor Yellow
        Write-Host "   Tables will be created when backend starts" -ForegroundColor Yellow
    }
} catch {
    Write-Host "   ⚠️  Could not check tables" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TEST COMPLETE" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
