# RateMyClass 📚

A modern web application for students to rate and review professors and courses at their university, built with Spring Boot and React.

![Build Status](https://github.com/mxs2/ratemyclass/workflows/CI%2FCD%20Pipeline/badge.svg)
![Coverage](https://codecov.io/gh/mxs2/ratemyclass/branch/main/graph/badge.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

## 🌟 Features

- **Professor & Course Reviews**: Comprehensive rating system with multiple criteria
- **Advanced Search**: Find professors and courses with powerful filtering options
- **Analytics Dashboard**: Visual insights and statistics for ratings and trends
- **User Authentication**: Secure login with university email verification
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile devices
- **Real-time Updates**: Live notifications and updates for new reviews
- **Anonymous Reviews**: Option to submit anonymous feedback
- **Department Organization**: Browse content by academic departments

## 🛠️ Technology Stack

### Backend
- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 3.x** - Enterprise-grade framework with auto-configuration
- **Spring Data JPA** - Data persistence layer with Hibernate
- **Spring Security** - Authentication and authorization
- **PostgreSQL** - Robust relational database
- **Maven** - Dependency management and build tool
- **Docker** - Containerization for consistent deployments

### Frontend
- **React 18+** - Modern UI library with hooks and concurrent features
- **TypeScript** - Type-safe JavaScript for better development experience
- **Ant Design 5.x** - Professional UI component library
- **Redux Toolkit** - Predictable state management
- **Vite** - Fast build tool and development server
- **Recharts** - Responsive charts and data visualization

## 🚀 Quick Start

### Using the Startup Scripts (Recommended)

The easiest way to run the project is using our cross-platform startup scripts:

**Windows:**
```cmd
# Navigate to the project folder
cd ratemyclass
scripts\start.bat
```

**Linux/macOS:**
```bash
# Navigate to the project folder
cd ratemyclass
./scripts/start.sh
```

The scripts will:
- Check prerequisites and port availability
- Start Docker services (PostgreSQL & Redis)
- Install frontend dependencies
- Start backend and frontend servers
- Provide status information and troubleshooting

See `scripts/SCRIPTS_README.md` for detailed information.

### Manual Setup

### Prerequisites
- Java 21 or higher
- Node.js 18+ and npm
- PostgreSQL 15+
- Docker and Docker Compose (optional)

### Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/mxs2/ratemyclass.git
   cd ratemyclass
   ```

2. **Database Setup**
   ```bash
   # Create PostgreSQL database
   createdb ratemyclass_dev
   
   # Or use Docker
   docker-compose -f docker-compose.dev.yml up database -d
   ```

3. **Backend Setup**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
   The API will be available at `http://localhost:8080`

4. **Frontend Setup**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   The frontend will be available at `http://localhost:3000`

### Docker Setup (Recommended)

1. **Run everything with Docker Compose**
   ```bash
   cd docker
   docker-compose up -d
   ```

2. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080
   - Database: localhost:5432

## 📁 Project Structure

```
ratemyclass/
├── backend/                    # Spring Boot backend
│   ├── src/main/java/
│   │   └── com/ratemyclass/
│   │       ├── entity/         # JPA entities
│   │       ├── repository/     # Data repositories
│   │       ├── service/        # Business logic
│   │       ├── controller/     # REST controllers
│   │       ├── dto/           # Data transfer objects
│   │       ├── config/        # Configuration classes
│   │       └── exception/     # Exception handling
│   ├── src/main/resources/
│   │   ├── application.yml    # Application configuration
│   │   └── db/migration/      # Database migrations
│   └── pom.xml               # Maven dependencies
├── frontend/                  # React frontend
│   ├── src/
│   │   ├── components/       # Reusable components
│   │   ├── pages/           # Page components
│   │   ├── services/        # API services
│   │   ├── store/          # Redux store
│   │   ├── types/          # TypeScript types
│   │   ├── hooks/          # Custom React hooks
│   │   └── utils/          # Utility functions
│   ├── public/             # Static assets
│   └── package.json        # npm dependencies
├── .github/workflows/       # CI/CD pipelines
├── docker/                  # Docker configuration
│   ├── docker-compose.yml       # Production Docker setup
│   ├── docker-compose.dev.yml   # Development Docker setup
│   └── README.md            # Docker documentation
├── scripts/                 # Startup and utility scripts
│   ├── start.sh/.bat        # Cross-platform start scripts
│   ├── stop.sh/.bat         # Cross-platform stop scripts
│   └── README.md            # Scripts documentation
└── README.md               # Project documentation
```

## 🔧 Configuration

### Backend Configuration

Key configuration files:
- `application.yml` - Main configuration
- `application-dev.yml` - Development settings
- `application-prod.yml` - Production settings

Environment variables:
```bash
DB_USERNAME=postgres
DB_PASSWORD=your_password
DATABASE_URL=jdbc:postgresql://localhost:5432/ratemyclass
JWT_SECRET=your_jwt_secret_key
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

### Frontend Configuration

Environment variables (create `.env` file):
```bash
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_NAME=RateMyClass
```

## 🧪 Testing

### Backend Tests
```bash
cd backend
./mvnw test                    # Run all tests
./mvnw test -Dtest=ClassName   # Run specific test
./mvnw jacoco:report          # Generate coverage report
```

### Frontend Tests
```bash
cd frontend
npm test                      # Run tests in watch mode
npm run test:coverage        # Run tests with coverage
npm run test:ui              # Run tests with UI
```

## 📦 Building for Production

### Manual Build
```bash
# Backend
cd backend
./mvnw clean package

# Frontend
cd frontend
npm run build
```

### Docker Build
```bash
# Build all services
docker-compose build

# Build specific service
docker-compose build backend
docker-compose build frontend
```

## 🚀 Deployment

### Using Docker Compose
```bash
# Production deployment
docker-compose -f docker-compose.yml up -d

# Check logs
docker-compose logs -f
```

### Environment-specific Deployments
```bash
# Development
docker-compose -f docker-compose.dev.yml up -d

# Staging
docker-compose -f docker-compose.staging.yml up -d
```

## 📊 API Documentation

Once the backend is running, access the interactive API documentation:
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

### Main API Endpoints

#### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/logout` - User logout

#### Professors
- `GET /api/v1/professors` - List professors
- `GET /api/v1/professors/{id}` - Get professor details
- `GET /api/v1/professors/{id}/ratings` - Get professor ratings

#### Courses
- `GET /api/v1/courses` - List courses
- `GET /api/v1/courses/{id}` - Get course details
- `GET /api/v1/courses/{id}/ratings` - Get course ratings

#### Ratings
- `POST /api/v1/ratings` - Submit rating
- `GET /api/v1/ratings` - List ratings with filters
- `PUT /api/v1/ratings/{id}` - Update rating
- `DELETE /api/v1/ratings/{id}` - Delete rating

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some amazing feature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

### Development Guidelines
- Follow the existing code style and conventions
- Write tests for new features
- Update documentation as needed
- Ensure all CI checks pass

## 🐛 Troubleshooting

### Common Issues

**Database Connection Issues**
```bash
# Check if PostgreSQL is running
pg_isready -U postgres

# Reset database
docker-compose down -v
docker-compose up database -d
```

**Frontend Build Issues**
```bash
# Clear node_modules and reinstall
rm -rf node_modules package-lock.json
npm install
```

**Backend Build Issues**
```bash
# Clean and rebuild
./mvnw clean compile
./mvnw dependency:resolve
```

### Logs and Debugging

```bash
# View application logs
docker-compose logs -f backend
docker-compose logs -f frontend

# Connect to database
docker exec -it ratemyclass-db psql -U postgres -d ratemyclass

# Check application health
curl http://localhost:8080/api/actuator/health
```

## 📈 Monitoring and Health Checks

- **Backend Health**: http://localhost:8080/api/actuator/health
- **Frontend Health**: http://localhost:3000/health
- **Database Metrics**: Available through Spring Boot Actuator

## 🔒 Security

- JWT-based authentication
- CORS protection
- SQL injection prevention
- XSS protection headers
- Input validation and sanitization
- Rate limiting on API endpoints

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- **Alessandra Barbosa de Santana**
- **Maria Gabriela Damásio Bezerra**
- **Mateus Xavier**
- **Raphael Rennan Soares de Miranda**
- **Rayane Cavalcanti da Silva**
- **Samuel Silva Araújo de Brito**

## 🔗 Links

- [Trello Board](https://trello.com/invite/b/68bc9a1653c4b185e984020e/ATTI63ea9e3314d386b2e6071f44e4dd5b1e73956C78/transparecesar) - Project management and user stories
- [Figma Design](https://www.figma.com/design/wNy2xI3GMrjVXo8YCp9tdT/Projeto-POO?node-id=13-12205&p=f&t=HfrxX5qF0bMNlxau-0) - UI/UX prototypes
- [YouTube Demo](https://youtu.be/bTy5LQIfXJw?si=_qeF84S4VDrcQJFO) - Project presentation

## 🆘 Support

If you encounter any issues or have questions:

1. Check the [troubleshooting section](#-troubleshooting)
2. Search existing [issues](https://github.com/mxs2/ratemyclass/issues)
3. Create a new issue with detailed information
4. Contact the development team

---

⭐ **Star this repository if you find it helpful!**
