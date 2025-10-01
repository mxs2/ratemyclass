# Copilot Instructions for RateMyClass

## Project Overview
- **RateMyClass** is a full-stack web app for university course and professor reviews.
- **Backend:** Java 21, Spring Boot 3.x, PostgreSQL, Redis, Maven
- **Frontend:** React 18+, TypeScript, Ant Design, Redux Toolkit, Vite
- **Containerization:** Docker & Docker Compose for local/dev/prod

## Architecture & Key Patterns
- **Backend** (`backend/`):
  - `entity/`: JPA entities (Course, Professor, User, etc.)
  - `repository/`: Spring Data JPA repositories
  - `service/`: Business logic (not shown in tree, but present)
  - `controller/`: REST API endpoints
  - `resources/db/migration/`: Flyway SQL migrations
  - Config split: `application.yml`, `application-dev.yml`, `application-prod.yml`
- **Frontend** (`frontend/`):
  - `components/`: Reusable UI components
  - `pages/`: Route-level React components
  - `services/api/`: API clients (e.g., `authApi.ts`)
  - `store/`: Redux Toolkit store
  - `types/`: TypeScript types for API and app state
- **Scripts** (`scripts/`):
  - `start.sh`/`start.bat`: Starts Docker DB/cache, backend, frontend, checks ports
  - `stop.sh`/`stop.bat`: Stops all services
  - Logs in `scripts/logs/`, PIDs in `scripts/pids/`
- **Docker** (`docker/`):
  - `docker-compose.dev.yml`: Dev DB/cache (ports 5433/6380)
  - `docker-compose.yml`: Full prod stack (DB, cache, backend, frontend, Nginx)

## Developer Workflows
- **Recommended:** Use `scripts/start.sh` (Linux/macOS) or `scripts/start.bat` (Windows) to launch all services for local dev.
- **Manual:**
  - Start DB/cache: `docker-compose -f docker/docker-compose.dev.yml up -d`
  - Backend: `cd backend && ./mvnw spring-boot:run`
  - Frontend: `cd frontend && npm install && npm run dev`
- **Logs:**
  - Backend: `scripts/logs/backend.log`
  - Frontend: `scripts/logs/frontend.log`
- **Config:**
  - Set env vars for DB, JWT, CORS, etc. (see `README.md`)
  - Use `application-dev.yml` for local dev

## Conventions & Patterns
- **Backend:**
  - Use DTOs for API payloads (see `controller/` and `dto/`)
  - Exception handling in `exception/` package
  - Service layer mediates between controllers and repositories
- **Frontend:**
  - API calls via `services/api/` using fetch/axios
  - State managed with Redux Toolkit (`store/`)
  - Auth logic in `hooks/useAuth.tsx` and `components/auth/`
  - Route protection via `components/auth/ProtectedRoute.tsx`
- **Testing:**
  - (Not detailed in docs; check for `test/` or `__tests__/` if present)

## Integration Points
- **Backend <-> Frontend:** REST API at `/api/*` (see controllers)
- **Backend <-> DB:** Spring Data JPA, Flyway migrations
- **Backend <-> Redis:** For caching/session (see config)
- **Frontend <-> Backend:** API base URL set in `services/api/client.ts`

## Examples
- Add a new entity: create in `entity/`, repo in `repository/`, service, controller, and migration SQL
- Add a new page: create in `pages/`, add route, connect to API via `services/api/`

## References
- See `README.md`, `scripts/SCRIPTS_README.md`, and `docker/README.md` for more details.

---
_Keep instructions concise and up-to-date. Update this file if project structure or workflows change._
