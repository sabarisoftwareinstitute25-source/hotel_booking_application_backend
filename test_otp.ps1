# PowerShell script to test OTP functionality

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  OTP FUNCTIONALITY TEST" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://localhost:8080"

# Test 1: Send Dummy OTP
Write-Host "1. Testing: Send Dummy OTP..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/test/otp/send-dummy" -Method POST -ContentType "application/json" -ErrorAction Stop
    
    if ($response.success) {
        Write-Host "   ✅ OTP Generated Successfully!" -ForegroundColor Green
        Write-Host "   Phone: $($response.phone)" -ForegroundColor Cyan
        Write-Host "   OTP Code: $($response.otp)" -ForegroundColor Cyan
        Write-Host "   Database ID: $($response.databaseId)" -ForegroundColor Cyan
        Write-Host "   Stored Code: $($response.storedCode)" -ForegroundColor Cyan
        Write-Host "   Total OTPs in DB: $($response.totalOtpsInDb)" -ForegroundColor Cyan
        
        if ($response.databaseId -ne $null) {
            Write-Host "   ✅ OTP Stored in Database!" -ForegroundColor Green
        } else {
            Write-Host "   ❌ OTP Not Stored in Database!" -ForegroundColor Red
        }
    } else {
        Write-Host "   ❌ Failed: $($response.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "   ❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "   Make sure backend is running on port 8080" -ForegroundColor Yellow
}

Write-Host ""

# Test 2: List All OTPs
Write-Host "2. Testing: List All OTPs..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/test/otp/list" -Method GET -ErrorAction Stop
    
    if ($response.success) {
        Write-Host "   ✅ Found $($response.totalCount) OTP(s) in database" -ForegroundColor Green
        
        if ($response.totalCount -gt 0) {
            $latest = $response.otps[0]
            Write-Host "   Latest OTP:" -ForegroundColor Cyan
            Write-Host "     ID: $($latest.id)" -ForegroundColor White
            Write-Host "     Phone: $($latest.phone)" -ForegroundColor White
            Write-Host "     Code: $($latest.code)" -ForegroundColor White
            Write-Host "     Used: $($latest.used)" -ForegroundColor White
            Write-Host "     Valid: $($latest.isValid)" -ForegroundColor White
        }
    }
} catch {
    Write-Host "   ❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Test 3: Health Check
Write-Host "3. Testing: Health Check..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/api/test/otp/health" -Method GET -ErrorAction Stop
    
    if ($response.success) {
        Write-Host "   ✅ OTP Service is Healthy!" -ForegroundColor Green
        Write-Host "   Total OTPs: $($response.totalOtps)" -ForegroundColor Cyan
        Write-Host "   Valid OTPs: $($response.validOtps)" -ForegroundColor Cyan
        Write-Host "   Expired OTPs: $($response.expiredOtps)" -ForegroundColor Cyan
    }
} catch {
    Write-Host "   ❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TEST COMPLETE" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next Steps:" -ForegroundColor Yellow
Write-Host "1. Check backend console for OTP logs" -ForegroundColor White
Write-Host "2. Verify in database: SELECT * FROM otps;" -ForegroundColor White
Write-Host "3. Test verification: GET /api/test/otp/verify/{phone}/{code}" -ForegroundColor White
Write-Host ""

