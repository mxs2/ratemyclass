@echo off
REM RateMyClass Project Stop Script for Windows
REM This script stops all services for the RateMyClass application

echo 🛑 Stopping RateMyClass Application
echo ===================================

REM Stop backend and frontend processes
echo 🔴 Stopping backend and frontend...
taskkill /f /im java.exe >nul 2>&1
taskkill /f /im node.exe >nul 2>&1
echo ✅ Application processes stopped

REM Stop Docker services
echo 🐳 Stopping Docker services...
docker-compose -f ..\docker\docker-compose.dev.yml down

REM Clean up log files (optional)
set /p cleanup="🗑️  Do you want to remove log files? (y/N): "
if /i "%cleanup%"=="y" (
    if exist "logs\backend.log" del "logs\backend.log"
    if exist "logs\frontend.log" del "logs\frontend.log"
    echo ✅ Log files removed
)

echo.
echo ✅ All services stopped!
echo =======================
pause