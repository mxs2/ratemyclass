# Development Guide

## Getting Started

This guide will help you set up and develop the RateMyClass application.

## Prerequisites

- Java 21 (OpenJDK or Oracle)
- Node.js 18+ and npm
- PostgreSQL 15+
- Git
- Docker (optional but recommended)

## Project Setup

### 1. Clone and Setup

```bash
git clone https://github.com/mxs2/ratemyclass.git
cd ratemyclass
```

### 2. Database Setup

#### Option A: Local PostgreSQL
```bash
# Create databases
createdb ratemyclass_dev
createdb ratemyclass_test

# Create user (optional)
psql -c "CREATE USER ratemyclass WITH PASSWORD 'password';"
psql -c "GRANT ALL PRIVILEGES ON DATABASE ratemyclass_dev TO ratemyclass;"
psql -c "GRANT ALL PRIVILEGES ON DATABASE ratemyclass_test TO ratemyclass;"
```

#### Option B: Docker PostgreSQL
```bash
docker-compose -f docker-compose.dev.yml up database -d
```

### 3. Backend Development

```bash
cd backend

# Install dependencies and run
./mvnw spring-boot:run

# Or with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The backend will be available at `http://localhost:8080`

#### Backend Development Commands

```bash
# Run tests
./mvnw test

# Run tests with coverage
./mvnw test jacoco:report

# Package without tests
./mvnw clean package -DskipTests

# Run with debugging (port 5005)
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```

### 4. Frontend Development

```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev

# Other useful commands
npm run build          # Build for production
npm run preview        # Preview production build
npm run lint           # Lint code
npm run lint:fix       # Fix linting issues
npm run type-check     # Check TypeScript types
npm test              # Run tests
npm run test:coverage # Run tests with coverage
```

The frontend will be available at `http://localhost:3000`

## Development Workflow

### 1. Feature Development

1. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**
   - Backend: Follow Spring Boot conventions
   - Frontend: Follow React and TypeScript best practices

3. **Test your changes**
   ```bash
   # Backend
   cd backend && ./mvnw test
   
   # Frontend
   cd frontend && npm test
   ```

4. **Commit and push**
   ```bash
   git add .
   git commit -m "feat: add your feature description"
   git push origin feature/your-feature-name
   ```

5. **Create Pull Request**

### 2. Code Style Guidelines

#### Backend (Java/Spring Boot)
- Use Java 21 features when appropriate
- Follow Spring Boot conventions
- Use meaningful variable and method names
- Write unit tests for services
- Use proper exception handling
- Document complex methods with JavaDoc

#### Frontend (React/TypeScript)
- Use TypeScript strict mode
- Follow React hooks patterns
- Use Ant Design components consistently
- Implement proper error boundaries
- Write unit tests for components
- Use proper state management with Redux Toolkit

### 3. Database Development

#### Adding Migrations
```bash
# Create new migration file
# backend/src/main/resources/db/migration/V{number}__{description}.sql

# Example: V3__add_user_preferences.sql
```

#### Migration Best Practices
- Always use versioned migrations
- Never modify existing migrations
- Test migrations on development database first
- Include rollback instructions in comments

## IDE Setup

### VS Code (Recommended)

#### Required Extensions
- **Java**: Extension Pack for Java
- **Spring**: Spring Boot Extension Pack
- **TypeScript**: TypeScript Importer
- **React**: ES7+ React/Redux/React-Native snippets
- **Docker**: Docker extension

#### Recommended Extensions
- **ESLint**: For code linting
- **Prettier**: For code formatting
- **GitLens**: Enhanced Git capabilities
- **Thunder Client**: API testing

#### VS Code Settings
```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.compile.nullAnalysis.mode": "automatic",
  "typescript.preferences.importModuleSpecifier": "relative",
  "editor.formatOnSave": true,
  "editor.codeActionsOnSave": {
    "source.organizeImports": true,
    "source.fixAll.eslint": true
  }
}
```

### IntelliJ IDEA

#### Setup Steps
1. Import Maven project from `backend/pom.xml`
2. Set Project SDK to Java 21
3. Enable annotation processing
4. Install Spring Boot plugin
5. Configure code style to match project

## Testing

### Backend Testing

#### Unit Tests
```java
@SpringBootTest
class UserServiceTest {
    
    @MockBean
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Test
    void shouldCreateUser() {
        // Test implementation
    }
}
```

#### Integration Tests
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserControllerIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
    
    @Test
    void shouldCreateUserEndpoint() {
        // Integration test implementation
    }
}
```

### Frontend Testing

#### Component Tests
```typescript
import { render, screen } from '@testing-library/react';
import { RatingCard } from './RatingCard';

test('renders rating card with professor name', () => {
  render(<RatingCard professor="John Doe" rating={4.5} />);
  expect(screen.getByText('John Doe')).toBeInTheDocument();
});
```

#### API Integration Tests
```typescript
import { rest } from 'msw';
import { setupServer } from 'msw/node';

const server = setupServer(
  rest.get('/api/professors', (req, res, ctx) => {
    return res(ctx.json({ professors: [] }));
  })
);

// Test implementation
```

## Debugging

### Backend Debugging

#### IntelliJ IDEA
1. Set breakpoints in your code
2. Run application in debug mode
3. Use "Debug 'RateMyClassApplication'"

#### VS Code
1. Create `.vscode/launch.json`:
```json
{
  "type": "java",
  "name": "Debug Spring Boot",
  "request": "launch",
  "mainClass": "com.ratemyclass.RateMyClassApplication",
  "projectName": "ratemyclass-backend"
}
```

### Frontend Debugging

#### Browser DevTools
- Use React Developer Tools extension
- Debug Redux state with Redux DevTools
- Network tab for API calls
- Console for runtime errors

#### VS Code
```json
{
  "type": "node",
  "request": "launch",
  "name": "Debug Frontend",
  "runtimeExecutable": "npm",
  "runtimeArgs": ["run", "dev"]
}
```

## Performance Tips

### Backend Performance
- Use database connection pooling (HikariCP)
- Implement caching with Spring Cache
- Optimize JPA queries with @Query
- Use pagination for large datasets
- Profile with JProfiler or async-profiler

### Frontend Performance
- Use React.memo for expensive components
- Implement proper code splitting
- Optimize bundle size with tree shaking
- Use Ant Design's built-in virtualization
- Monitor with React DevTools Profiler

## Troubleshooting

### Common Backend Issues

**Port Already in Use**
```bash
# Find process using port 8080
lsof -i :8080
# Kill the process
kill -9 <PID>
```

**Database Connection Failed**
```bash
# Check PostgreSQL status
pg_isready -U postgres
# Restart PostgreSQL
sudo service postgresql restart
```

### Common Frontend Issues

**Node Modules Issues**
```bash
# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm cache clean --force
npm install
```

**Type Errors**
```bash
# Check TypeScript configuration
npm run type-check
# Update type definitions
npm update @types/*
```

## Useful Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [React Documentation](https://react.dev/)
- [Ant Design Components](https://ant.design/components/overview/)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

## Getting Help

- Check existing [GitHub Issues](https://github.com/mxs2/ratemyclass/issues)
- Ask questions in team Slack/Discord
- Review code with pull requests
- Consult team documentation on Trello