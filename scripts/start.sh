#!/bin/bash

# RateMyClass Project Start Script for Linux/macOS
# This script starts all services needed for the RateMyClass application

set -e

echo "🚀 Starting RateMyClass Application"
echo "=================================="

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and try again."
    exit 1
fi

# Check if Docker Compose is available
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose and try again."
    exit 1
fi

# Function to check if port is in use
check_port() {
    local port=$1
    if lsof -Pi :$port -sTCP:LISTEN -t >/dev/null 2>&1; then
        echo "⚠️  Port $port is already in use. Please stop the service using that port."
        return 1
    fi
    return 0
}

echo "📋 Checking prerequisites..."

# Check required ports
PORTS_OK=true
if ! check_port 8080; then
    echo "   Backend port 8080 is in use"
    PORTS_OK=false
fi

if ! check_port 3000; then
    echo "   Frontend port 3000 is in use"
    PORTS_OK=false
fi

if ! check_port 5433; then
    echo "   Database port 5433 is in use"
    PORTS_OK=false
fi

if ! check_port 6380; then
    echo "   Redis port 6380 is in use"
    PORTS_OK=false
fi

if [ "$PORTS_OK" = false ]; then
    echo "❌ Some required ports are in use. Please stop those services first."
    echo ""
    echo "💡 Quick fix: Run './stop.sh' to automatically stop services on these ports"
    echo "   Or manually check what's using these ports with: lsof -i :PORT_NUMBER"
    exit 1
fi

echo "✅ All prerequisites met"

# Start Docker services
echo "🐳 Starting Docker services (PostgreSQL & Redis)..."
docker-compose -f ../docker/docker-compose.dev.yml up -d

# Wait for database to be ready
echo "⏳ Waiting for database to be ready..."
sleep 5

# Check if database is ready
while ! docker-compose -f ../docker/docker-compose.dev.yml exec -T database pg_isready -h localhost -p 5432 > /dev/null 2>&1; do
    echo "   Database not ready yet, waiting..."
    sleep 2
done

echo "✅ Database is ready"

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js and try again."
    exit 1
fi

# Check if npm is installed
if ! command -v npm &> /dev/null; then
    echo "❌ npm is not installed. Please install npm and try again."
    exit 1
fi

# Install frontend dependencies if needed
if [ ! -d "../frontend/node_modules" ]; then
    echo "📦 Installing frontend dependencies..."
    cd ../frontend
    npm install
    cd ../scripts
fi

# Function to start backend
start_backend() {
    echo "🔧 Starting backend server..."
    cd ../backend
    ./mvnw spring-boot:run > ../scripts/logs/backend.log 2>&1 &
    BACKEND_PID=$!
    echo $BACKEND_PID > ../scripts/pids/backend.pid
    cd ../scripts
    echo "   Backend starting... (PID: $BACKEND_PID)"
}

# Function to start frontend
start_frontend() {
    echo "⚛️  Starting frontend server..."
    cd ../frontend
    npm run dev > ../scripts/logs/frontend.log 2>&1 &
    FRONTEND_PID=$!
    echo $FRONTEND_PID > ../scripts/pids/frontend.pid
    cd ../scripts
    echo "   Frontend starting... (PID: $FRONTEND_PID)"
}

# Start backend
start_backend

# Wait a moment for backend to start
sleep 3

# Start frontend
start_frontend

# Wait for services to start
echo "⏳ Waiting for services to start..."
sleep 10

# Check if services are running
echo "🔍 Checking service status..."

# Check backend
if curl -s http://localhost:8080/api/actuator/health > /dev/null 2>&1; then
    echo "✅ Backend is running on http://localhost:8080"
else
    echo "⚠️  Backend might still be starting... Check backend.log for details"
fi

# Check frontend
if curl -s http://localhost:3000 > /dev/null 2>&1; then
    echo "✅ Frontend is running on http://localhost:3000"
else
    echo "⚠️  Frontend might still be starting... Check frontend.log for details"
fi

echo ""
echo "🎉 RateMyClass is starting up!"
echo "=================================="
echo "Frontend: http://localhost:3000"
echo "Backend:  http://localhost:8080"
echo "Database: localhost:5433"
echo "Redis:    localhost:6380"
echo ""
echo "📝 Logs:"
echo "Backend:  tail -f logs/backend.log"
echo "Frontend: tail -f logs/frontend.log"
echo ""
echo "🛑 To stop all services, run: ./stop.sh"
echo ""
echo "⏳ Services are starting... Please wait a moment before accessing the application."