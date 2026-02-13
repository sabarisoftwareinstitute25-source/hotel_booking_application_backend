# PowerShell script to test OTP generation and verification

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  OTP VERIFICATION TEST" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://localhost:8080"
$testPhone = "+919876543210"

# Step 1: Send OTP
Write-Host "Step 1: Sending OTP..." -ForegroundColor Yellow
try {
    $sendResponse = Invoke-RestMethod -Uri "$baseUrl/api/test/otp/send-dummy" `
        -Method POST `
        -ContentType "application/json" `
        -Body (@{phone = $testPhone} | ConvertTo-Json) `
        -ErrorAction Stop
    
    if ($sendResponse.success) {
        Write-Host "   ✅ OTP Generated!" -ForegroundColor Green
        Write-Host "   Phone: $($sendResponse.phone)" -ForegroundColor Cyan
        Write-Host "   OTP Code: $($sendResponse.otp)" -ForegroundColor Cyan
        Write-Host "   Database ID: $($sendResponse.databaseId)" -ForegroundColor Cyan
        
        $otpCode = $sendResponse.otp
        
        Write-Host ""
        Write-Host "Step 2: Verifying OTP..." -ForegroundColor Yellow
        
        # Step 2: Verify OTP
        $verifyBody = @{
            phone = $testPhone
            otp = $otpCode
        } | ConvertTo-Json
        
        $verifyResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/verify-phone-otp" `
            -Method POST `
            -ContentType "application/json" `
            -Body $verifyBody `
            -ErrorAction Stop
        
        if ($verifyResponse.verified) {
            Write-Host "   ✅ OTP Verified Successfully!" -ForegroundColor Green
            Write-Host "   Message: $($verifyResponse.message)" -ForegroundColor Cyan
        } else {
            Write-Host "   ❌ OTP Verification Failed!" -ForegroundColor Red
            Write-Host "   Message: $($verifyResponse.message)" -ForegroundColor Yellow
        }
    } else {
        Write-Host "   ❌ Failed to generate OTP" -ForegroundColor Red
        Write-Host "   Message: $($sendResponse.message)" -ForegroundColor Yellow
    }
} catch {
    Write-Host "   ❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "   Make sure backend is running on port 8080" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Step 3: Checking database..." -ForegroundColor Yellow
Write-Host "   Run: SELECT * FROM otps WHERE phone = '$testPhone';" -ForegroundColor Cyan

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TEST COMPLETE" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Check backend logs for detailed verification info" -ForegroundColor Yellow
Write-Host ""

