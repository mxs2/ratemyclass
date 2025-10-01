@echo off
REM RateMyClass Project Stop Script for Windows
REM This script stops all services for the RateMyClass application
REM 
REM Enhanced features:
REM - Stops processes by port number (more reliable)
REM - Checks ports 3000, 8080, 5433, 6380
REM - Provides detailed feedback on what was stopped
REM - Verifies all ports are clear after stopping
REM - Falls back to stopping by process name if needed

echo 🛑 Stopping RateMyClass Application
echo ===================================

REM Function to check if port is in use and get PID
REM Usage: call :get_port_pid PORT_NUMBER
:get_port_pid
setlocal
set "port=%1"
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":%port% "') do (
    set "pid=%%a"
    goto :found_pid
)
set "pid="
:found_pid
endlocal & set "port_pid=%pid%"
goto :eof

REM Function to stop process by port
REM Usage: call :stop_by_port SERVICE_NAME PORT_NUMBER
:stop_by_port
setlocal
set "service_name=%1"
set "port=%2"
call :get_port_pid %port%
if not "%port_pid%"=="" (
    echo 🔴 Found %service_name% running on port %port% ^(PID: %port_pid%^)
    echo    Stopping %service_name%...
    taskkill /f /pid %port_pid% >nul 2>&1
    timeout /t 2 /nobreak >nul
    call :get_port_pid %port%
    if not "%port_pid%"=="" (
        echo    Force stopping %service_name%...
        taskkill /f /pid %port_pid% >nul 2>&1
    )
    echo ✅ %service_name% stopped
) else (
    echo ℹ️  %service_name% not running on port %port%
)
endlocal
goto :eof

REM Stop processes by port (more reliable than process name)
echo 🔍 Checking and stopping services by port...

REM Stop frontend (port 3000)
call :stop_by_port "Frontend" 3000

REM Stop backend (port 8080)
call :stop_by_port "Backend" 8080

REM Stop any processes on database port (port 5433) - though usually Docker
call :stop_by_port "Database" 5433

REM Stop any processes on Redis port (port 6380) - though usually Docker
call :stop_by_port "Redis" 6380

echo.
echo � Stopping common processes as backup...

REM Stop backend and frontend processes by name (backup method)
echo 🔴 Stopping Java and Node.js processes...
taskkill /f /im java.exe >nul 2>&1
taskkill /f /im node.exe >nul 2>&1
echo ✅ Application processes stopped

REM Stop Docker services
echo.
echo 🐳 Stopping Docker services...
docker-compose -f ..\docker\docker-compose.dev.yml down

REM Final verification
echo.
echo 🔍 Final port verification...
set "ports_clear=true"

for %%p in (3000 8080 5433 6380) do (
    call :get_port_pid %%p
    if not "%port_pid%"=="" (
        echo ⚠️  Port %%p is still in use ^(PID: %port_pid%^)
        set "ports_clear=false"
    ) else (
        echo ✅ Port %%p is clear
    )
)

if "%ports_clear%"=="false" (
    echo.
    echo ⚠️  Some ports are still in use. You may need to manually stop those processes.
    echo    Use 'netstat -ano ^| findstr :PORT_NUMBER' to identify processes
    echo    Use 'taskkill /f /pid PID_NUMBER' to force stop if needed
)

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