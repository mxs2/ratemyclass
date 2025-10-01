# Docker Configuration

This folder contains Docker and Docker Compose configurations for the RateMyClass application.

## Files

- `docker-compose.yml` - Production Docker Compose configuration
- `docker-compose.dev.yml` - Development Docker Compose configuration

## Services

### Development Environment (`docker-compose.dev.yml`)
- **Database**: PostgreSQL 15 on port 5433
- **Redis**: Redis 7 on port 6380

### Production Environment (`docker-compose.yml`)
- **Database**: PostgreSQL 15 on port 5432
- **Redis**: Redis 7 on port 6379
- **Backend**: Spring Boot application
- **Frontend**: Nginx serving React build
- **Reverse Proxy**: Nginx

## Usage

### Development
```bash
# Start only database and Redis
docker-compose -f docker/docker-compose.dev.yml up -d

# Stop services
docker-compose -f docker/docker-compose.dev.yml down
```

### Production
```bash
# Start all services
docker-compose -f docker/docker-compose.yml up -d

# Stop services
docker-compose -f docker/docker-compose.yml down
```

## Notes

- Development uses different ports to avoid conflicts with local development
- Production configuration includes the full application stack
- Use the scripts in the `scripts/` folder for automated setup