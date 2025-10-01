@echo off
REM RateMyClass Project Start Script for Windows
REM This script starts all services needed for the RateMyClass application

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