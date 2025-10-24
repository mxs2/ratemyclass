# RateMyClass 📚

A modern web application for students to rate and review professors and courses at their university, built with Spring Boot and React.

![Build Status](https://github.com/mxs2/ratemyclass/workflows/CI%2FCD%20Pipeline/badge.svg)
![Coverage](https://codecov.io/gh/mxs2/ratemyclass/branch/main/graph/badge.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)


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
- [YouTube](https://youtu.be/bTy5LQIfXJw?si=_qeF84S4VDrcQJFO) - Project presentation (Entrega 1)
- [YouTube](https://youtu.be/IOHdonVIenQ) - Project presentation (Entrega 2)
- [YouTube](https://youtu.be/_Jk1oFpy5wU) -  Screencast HUs (Entrega 3) 
- [YouTube](https://youtu.be/mW9EzpxDMXs) - Screencast Teste (Entrega 3)

## 🌟 Features

- **HU-001** https://trello.com/c/zAmOcMLL
- **HU-002** https://trello.com/c/uX0xmtO0
- **HU-003** https://trello.com/c/5rhhCx63
- **HU-004** https://trello.com/c/XscGvAYQ
- **HU-005** https://trello.com/c/ZSDhwQWt
- **HU-006** https://trello.com/c/dBQdX0CE
- **HU-007** https://trello.com/c/eG63XmgC
-  **HU-008** https://trello.com/c/ALVgJ878

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
│   ├── logs/                # Application log files
│   ├── pids/                # Process ID files
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

## 📊 API Documentation

Once the backend is running, access the interactive API documentation:
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

### Main API Endpoints

#### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/logout` - User logout

#### Disciplinas
- `GET /disciplinas` - Lista disciplinas

#### Professores
- `GET /professores` - Lista professores

#### Coordenadores
- `GET /coordenadores` - Lista coordenadores

#### Avaliações
- `POST /avaliacoes/professor` - Avaliação Professor
- `POST /avaliacoes/coordenador` - Avaliação Coordenador
- `POST /avaliacoes/disciplina` - Avaliação Disciplina

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

---

## Tables

**Registro de avaliações:**
- avaliacao_coordenador
- avaliacao_professor
- avaliacao_disciplina

**Listagem dos Professores, Coordenadores e Disciplinas**
- coordenadores
- professores
- disciplinas

⭐ **Star this repository if you find it helpful!**
