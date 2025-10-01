# RateMyClass - Quick Start Scripts

This folder contains scripts to easily start and stop the RateMyClass application on different operating systems.

## Prerequisites

Before running the scripts, make sure you have the following installed:

### All Platforms
- **Docker Desktop** (includes Docker and Docker Compose)
  - Windows: https://docs.docker.com/desktop/install/windows/
  - macOS: https://docs.docker.com/desktop/install/mac/
  - Linux: https://docs.docker.com/desktop/install/linux/

- **Node.js** (v18 or later) and npm
  - Download: https://nodejs.org/

### Linux/macOS Additional Requirements
- **curl** (usually pre-installed)
- **lsof** (usually pre-installed)

## Quick Start

### Windows
```cmd
# Start the application
start.bat

# Stop the application
stop.bat
```

### Linux/macOS
```bash
# Make scripts executable (first time only)
chmod +x start.sh stop.sh

# Start the application
./start.sh

# Stop the application
./stop.sh
```

## What the Start Script Does

1. **Checks Prerequisites**
   - Verifies Docker is running
   - Checks if required ports are available (3000, 8080, 5433, 6380)
   - Ensures Node.js and npm are installed

2. **Starts Services**
   - Launches PostgreSQL and Redis using Docker Compose
   - Waits for database to be ready
   - Installs frontend dependencies (if needed)
   - Starts the Spring Boot backend server
   - Starts the React frontend development server

3. **Provides Status Information**
   - Shows service URLs and ports
   - Indicates where to find logs
   - Provides stop instructions

## Service URLs

After starting, the application will be available at:

- **Frontend (React)**: http://localhost:3000
- **Backend (Spring Boot)**: http://localhost:8080
- **Database (PostgreSQL)**: localhost:5433
- **Redis**: localhost:6380

## Logs

The scripts create log files for debugging:

- `logs/backend.log` - Spring Boot application logs
- `logs/frontend.log` - React development server logs

### Viewing Logs

**Windows:**
```cmd
type logs\backend.log
type logs\frontend.log
```

**Linux/macOS:**
```bash
tail -f logs/backend.log
tail -f logs/frontend.log
```

## Troubleshooting

### Port Already in Use
If you get port conflicts, check what's using the ports:

**Windows:**
```cmd
netstat -ano | findstr :8080
netstat -ano | findstr :3000
```

**Linux/macOS:**
```bash
lsof -i :8080
lsof -i :3000
```

### Docker Issues
- Make sure Docker Desktop is running
- Try restarting Docker Desktop
- On Windows, ensure WSL 2 is properly configured

### Database Connection Issues
- Wait a bit longer for the database to start
- Check Docker logs: `docker-compose -f docker-compose.dev.yml logs database`

### Frontend Dependencies
If the frontend fails to start:
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

### Backend Compilation Issues
If the backend fails to compile:
```bash
cd backend
./mvnw clean compile
# On Windows: mvnw.cmd clean compile
```

## Manual Startup (Alternative)

If the scripts don't work, you can start services manually:

### 1. Start Docker Services
```bash
docker-compose -f docker-compose.dev.yml up -d
```

### 2. Start Backend
```bash
cd backend
./mvnw spring-boot:run
# On Windows: mvnw.cmd spring-boot:run
```

### 3. Start Frontend (in another terminal)
```bash
cd frontend
npm install
npm run dev
```

## Development Mode

The scripts start the application in development mode with:
- Hot reloading for frontend changes
- Automatic restart for backend changes (Spring Boot DevTools)
- Database with sample data
- CORS enabled for cross-origin requests

## Production Setup

For production deployment, use:
```bash
docker-compose up -d
```

This uses the production Docker Compose configuration with optimized builds.

## Need Help?

If you encounter issues:

1. Check the logs in `logs/backend.log` and `logs/frontend.log`
2. Ensure all prerequisites are installed
3. Try the manual startup steps
4. Restart Docker Desktop
5. Check the main README.md for detailed setup instructions