#!/bin/bash

# RateMyClass Project Stop Script for Linux/macOS
# This script stops all services for the RateMyClass application

set -e

echo "🛑 Stopping RateMyClass Application"
echo "==================================="

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

# Stop backend
stop_service "Backend" "pids/backend.pid"

# Stop frontend
stop_service "Frontend" "pids/frontend.pid"

# Stop Docker services
echo "🐳 Stopping Docker services..."
docker-compose -f ../docker/docker-compose.dev.yml down

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