$ErrorActionPreference = 'Stop'

Write-Host "========================================"
Write-Host "Starting Hotel Booking Backend (Safe)"
Write-Host "========================================"
Write-Host ""

# Kill anything listening on port 8080
$pids = @(
  netstat -ano |
    Select-String -Pattern '[:.]8080\s+.*LISTENING\s+\d+$' |
    ForEach-Object {
      ($_ -split '\s+')[-1]
    } |
    Where-Object { $_ -match '^\d+$' } |
    Select-Object -Unique
)

if ($pids.Count -gt 0) {
  Write-Host "Port 8080 is in use. Stopping PID(s): $($pids -join ', ')"
  foreach ($pid in $pids) {
    try {
      Stop-Process -Id ([int]$pid) -Force -ErrorAction Stop
    } catch {
      Write-Warning "Failed to stop PID $pid : $($_.Exception.Message)"
    }
  }
  Start-Sleep -Seconds 1
} else {
  Write-Host "Port 8080 is free."
}

# Ensure JAVA_HOME points to a real JDK (mvnw.cmd requires it)
$jdk21 = 'C:\Program Files\Java\jdk-21'
if (-not (Test-Path "$jdk21\bin\java.exe")) {
  throw "JDK 21 not found at '$jdk21'. Install JDK 21 or update START_BACKEND_SAFE.ps1."
}

$env:JAVA_HOME = $jdk21
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
Write-Host "Using JAVA_HOME: $env:JAVA_HOME"

Write-Host ""
Write-Host "Starting Spring Boot backend on http://localhost:8080"
Write-Host "Press Ctrl+C to stop."
Write-Host ""

Set-Location $PSScriptRoot
& .\mvnw.cmd -DskipTests spring-boot:run


