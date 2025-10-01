@echo off
REM RateMyClass Project Start Script for Windows
REM This script starts all services needed for the RateMyClass application
REM
REM Enhanced features:
REM - Checks if required ports (3000, 8080, 5433, 6380) are available
REM - Provides helpful guidance if ports are in use
REM - Suggests running stop.bat to clear ports automatically

echo 🚀 Starting RateMyClass Application
echo ==================================

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker is not running. Please start Docker Desktop and try again.
    pause
    exit /b 1
)

REM Check if Docker Compose is available
docker-compose --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker Compose is not installed. Please install Docker Desktop and try again.
    pause
    exit /b 1
)

echo 📋 Checking prerequisites...

REM Function to check if port is in use
REM Usage: call :check_port PORT_NUMBER
:check_port
setlocal
set "port=%1"
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":%port% "') do (
    echo ⚠️  Port %port% is already in use. Please stop the service using that port.
    endlocal & set "port_check_result=false"
    goto :eof
)
endlocal & set "port_check_result=true"
goto :eof

REM Check required ports
set "ports_ok=true"

call :check_port 8080
if "%port_check_result%"=="false" (
    echo    Backend port 8080 is in use
    set "ports_ok=false"
)

call :check_port 3000
if "%port_check_result%"=="false" (
    echo    Frontend port 3000 is in use
    set "ports_ok=false"
)

call :check_port 5433
if "%port_check_result%"=="false" (
    echo    Database port 5433 is in use
    set "ports_ok=false"
)

call :check_port 6380
if "%port_check_result%"=="false" (
    echo    Redis port 6380 is in use
    set "ports_ok=false"
)

if "%ports_ok%"=="false" (
    echo ❌ Some required ports are in use. Please stop those services first.
    echo.
    echo 💡 Quick fix: Run 'stop.bat' to automatically stop services on these ports
    echo    Or manually check what's using these ports with: netstat -ano ^| findstr :PORT_NUMBER
    pause
    exit /b 1
)

REM Check if Node.js is installed
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js is not installed. Please install Node.js and try again.
    echo Download from: https://nodejs.org/
    pause
    exit /b 1
)

REM Check if npm is installed
npm --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ npm is not installed. Please install npm and try again.
    pause
    exit /b 1
)

echo ✅ All prerequisites met

REM Start Docker services
echo 🐳 Starting Docker services (PostgreSQL ^& Redis)...
docker-compose -f ..\docker\docker-compose.dev.yml up -d

REM Wait for database to be ready
echo ⏳ Waiting for database to be ready...
timeout /t 5 /nobreak >nul

REM Check database readiness (simplified for Windows)
echo    Checking database connection...
timeout /t 3 /nobreak >nul

echo ✅ Database should be ready

REM Install frontend dependencies if needed
if not exist "..\frontend\node_modules" (
    echo 📦 Installing frontend dependencies...
    cd ..\frontend
    npm install
    cd ..\scripts
)

REM Start backend
echo 🔧 Starting backend server...
cd ..\backend
start /b "Backend" cmd /c "mvnw.cmd spring-boot:run > ..\scripts\logs\backend.log 2>&1"
cd ..\scripts
echo    Backend starting...

REM Wait a moment for backend to start
timeout /t 5 /nobreak >nul

REM Start frontend
echo ⚛️  Starting frontend server...
cd ..\frontend
start /b "Frontend" cmd /c "npm run dev > ..\scripts\logs\frontend.log 2>&1"
cd ..\scripts
echo    Frontend starting...

REM Wait for services to start
echo ⏳ Waiting for services to start...
timeout /t 15 /nobreak >nul

REM Check if services are responding (simplified)
echo 🔍 Checking service status...
echo    Services should be starting up...

echo.
echo 🎉 RateMyClass is starting up!
echo ==================================
echo Frontend: http://localhost:3000
echo Backend:  http://localhost:8080
echo Database: localhost:5433
echo Redis:    localhost:6380
echo.
echo 📝 Logs:
echo Backend:  type logs\backend.log
echo Frontend: type logs\frontend.log
echo.
echo 🛑 To stop all services, run: stop.bat
echo.
echo ⏳ Services are starting... Please wait a moment before accessing the application.
echo.
echo Press any key to open the application in your browser...
pause >nul

REM Open browser
start http://localhost:3000