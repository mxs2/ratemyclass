# RateMyClass - Startup Scripts

This folder contains cross-platform scripts to easily start and stop the RateMyClass application.

## Files

- `start.sh` - Linux/macOS startup script
- `start.bat` - Windows startup script  
- `stop.sh` - Linux/macOS shutdown script
- `stop.bat` - Windows shutdown script
- `SCRIPTS_README.md` - Detailed documentation and troubleshooting
- `logs/` - Application log files (backend.log, frontend.log)
- `pids/` - Process ID files for running services

## Quick Usage

### Windows
```cmd
scripts\start.bat    # Start all services
scripts\stop.bat     # Stop all services
```

### Linux/macOS
```bash
./scripts/start.sh   # Start all services
./scripts/stop.sh    # Stop all services
```

## What Gets Started

- PostgreSQL database (Docker)
- Redis cache (Docker)
- Spring Boot backend (port 8080)
- React frontend (port 3000)

## For More Information

See `SCRIPTS_README.md` for detailed documentation, prerequisites, and troubleshooting guide.