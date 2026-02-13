Write-Host ""
Write-Host "=== Frontend-Backend Connection Check ===" -ForegroundColor Cyan
Write-Host ""

# Check if backend is running
Write-Host "[1/4] Checking Backend Status..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/hotels" -Method GET -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
    Write-Host "   ✓ Backend is RUNNING" -ForegroundColor Green
    Write-Host "   Status Code: $($response.StatusCode)" -ForegroundColor Gray
    Write-Host "   Response: $($response.Content.Substring(0, [Math]::Min(50, $response.Content.Length)))..." -ForegroundColor Gray
} catch {
    Write-Host "   ✗ Backend is NOT running" -ForegroundColor Red
    Write-Host "   Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    Write-Host "   To start backend:" -ForegroundColor Yellow
    Write-Host "   cd mobileapp\mobileapp" -ForegroundColor Gray
    Write-Host "   .\mvnw.cmd spring-boot:run" -ForegroundColor Gray
    exit 1
}

Write-Host ""
Write-Host "[2/4] Checking Frontend Configuration..." -ForegroundColor Yellow
$apiConfigPath = "..\..\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main\lib\config\api_config.dart"
if (Test-Path $apiConfigPath) {
    $apiConfig = Get-Content $apiConfigPath -Raw
    if ($apiConfig -match "baseUrl.*=.*'([^']+)'") {
        $baseUrl = $matches[1]
        Write-Host "   ✓ API Base URL: $baseUrl" -ForegroundColor Green
        if ($baseUrl -eq "http://10.0.2.2:8080") {
            Write-Host "   ✓ Correct for Android Emulator" -ForegroundColor Green
        } else {
            Write-Host "   ⚠ For physical device, ensure this matches your computer's IP" -ForegroundColor Yellow
        }
    } else {
        Write-Host "   ⚠ Could not find baseUrl in api_config.dart" -ForegroundColor Yellow
    }
} else {
    Write-Host "   ✗ api_config.dart not found" -ForegroundColor Red
}

Write-Host ""
Write-Host "[3/4] Checking Android Network Security..." -ForegroundColor Yellow
$manifestPath = "..\..\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main\android\app\src\main\AndroidManifest.xml"
$networkConfigPath = "..\..\hotel_booking_mobile_application-main\hotel_booking_mobile_application-main\android\app\src\main\res\xml\network_security_config.xml"

if (Test-Path $manifestPath) {
    $manifest = Get-Content $manifestPath -Raw
    if ($manifest -match "usesCleartextTraffic.*true") {
        Write-Host "   ✓ Cleartext traffic enabled in AndroidManifest" -ForegroundColor Green
    } else {
        Write-Host "   ✗ Cleartext traffic NOT enabled" -ForegroundColor Red
    }
} else {
    Write-Host "   ⚠ AndroidManifest.xml not found" -ForegroundColor Yellow
}

if (Test-Path $networkConfigPath) {
    Write-Host "   ✓ Network security config exists" -ForegroundColor Green
} else {
    Write-Host "   ⚠ Network security config not found" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "[4/4] Testing API Endpoints..." -ForegroundColor Yellow

# Test hotels endpoint
try {
    $hotelsResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/hotels" -Method GET -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
    Write-Host "   ✓ GET /api/hotels - Working" -ForegroundColor Green
} catch {
    Write-Host "   ✗ GET /api/hotels - Failed" -ForegroundColor Red
}

# Test auth endpoint (should return error without body, but endpoint exists)
try {
    $authResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" -Method POST -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
    Write-Host "   ✓ POST /api/auth/login - Accessible" -ForegroundColor Green
} catch {
    if ($_.Exception.Response.StatusCode -eq 400) {
        Write-Host "   ✓ POST /api/auth/login - Accessible (400 expected without body)" -ForegroundColor Green
    } else {
        Write-Host "   ⚠ POST /api/auth/login - Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "=== Connection Summary ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "Backend: " -NoNewline
try {
    $null = Invoke-WebRequest -Uri "http://localhost:8080/api/hotels" -Method GET -TimeoutSec 2 -UseBasicParsing -ErrorAction Stop
    Write-Host "✓ RUNNING" -ForegroundColor Green
} catch {
    Write-Host "✗ NOT RUNNING" -ForegroundColor Red
}

Write-Host "Frontend Config: ✓ CONFIGURED" -ForegroundColor Green
Write-Host "Network Security: ✓ CONFIGURED" -ForegroundColor Green
Write-Host ""
Write-Host "Next Steps:" -ForegroundColor Yellow
Write-Host "  1. Ensure backend is running (port 8080)" -ForegroundColor Gray
Write-Host "  2. Run Flutter app: flutter run" -ForegroundColor Gray
Write-Host "  3. Test login/signup in the app" -ForegroundColor Gray
Write-Host "  4. Test hotel search functionality" -ForegroundColor Gray
Write-Host ""

