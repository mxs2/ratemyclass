#!/bin/bash

# RateMyClass Project Stop Script for Linux/macOS
# This script stops all services for the RateMyClass application

set -e

echo "🛑 Stopping RateMyClass Application"
echo "==================================="

# Function to check if port is in use and get PID
get_port_pid() {
    local port=$1
    lsof -Pi :$port -sTCP:LISTEN -t 2>/dev/null || echo ""
}

# Function to stop process by port
stop_by_port() {
    local service_name=$1
    local port=$2
    
    local pid=$(get_port_pid $port)
    if [ -n "$pid" ]; then
        echo "🔴 Found $service_name running on port $port (PID: $pid)"
        echo "   Stopping $service_name..."
        kill $pid 2>/dev/null || true
        sleep 2
        # Force kill if still running
        pid=$(get_port_pid $port)
        if [ -n "$pid" ]; then
            echo "   Force stopping $service_name..."
            kill -9 $pid 2>/dev/null || true
        fi
        echo "✅ $service_name stopped"
    else
        echo "ℹ️  $service_name not running on port $port"
    fi
}

# Function to stop process by PID file
stop_service() {
    local service_name=$1
    local pid_file=$2
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if ps -p $pid > /dev/null 2>&1; then
            echo "🔴 Stopping $service_name (PID: $pid)..."
            kill $pid
            sleep 2
            # Force kill if still running
            if ps -p $pid > /dev/null 2>&1; then
                echo "   Force stopping $service_name..."
                kill -9 $pid
            fi
            echo "✅ $service_name stopped"
        else
            echo "⚠️  $service_name was not running"
        fi
        rm -f "$pid_file"
    else
        echo "⚠️  No PID file found for $service_name"
    fi
}

# Stop processes by port (more reliable than PID files)
echo "🔍 Checking and stopping services by port..."

# Stop frontend (port 3000)
stop_by_port "Frontend" 3000

# Stop backend (port 8080)
stop_by_port "Backend" 8080

# Stop any processes on database port (port 5433) - though usually Docker
stop_by_port "Database" 5433

# Stop any processes on Redis port (port 6380) - though usually Docker
stop_by_port "Redis" 6380

echo ""
echo "🔍 Checking PID files as backup..."

# Stop backend by PID file (backup method)
stop_service "Backend" "pids/backend.pid"

# Stop frontend by PID file (backup method)
stop_service "Frontend" "pids/frontend.pid"

# Stop Docker services
echo ""
echo "🐳 Stopping Docker services..."
docker-compose -f ../docker/docker-compose.dev.yml down

# Final verification
echo ""
echo "🔍 Final port verification..."
PORTS_CLEAR=true

for port in 3000 8080 5433 6380; do
    pid=$(get_port_pid $port)
    if [ -n "$pid" ]; then
        echo "⚠️  Port $port is still in use (PID: $pid)"
        PORTS_CLEAR=false
    else
        echo "✅ Port $port is clear"
    fi
done

if [ "$PORTS_CLEAR" = false ]; then
    echo ""
    echo "⚠️  Some ports are still in use. You may need to manually stop those processes."
    echo "   Use 'sudo lsof -i :PORT_NUMBER' to identify processes"
    echo "   Use 'sudo kill -9 PID' to force stop if needed"
fi

# Clean up log files (optional)
read -p "🗑️  Do you want to remove log files? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    rm -f logs/backend.log logs/frontend.log
    echo "✅ Log files removed"
fi

echo ""
echo "✅ All services stopped!"
echo "======================="