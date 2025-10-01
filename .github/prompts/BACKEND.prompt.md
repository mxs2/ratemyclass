---
mode: agent
---

# RateMyClass Backend Development Prompt

## Project Overview
Develop a robust backend system for **RateMyClass**, a platform that enables students to evaluate professors and courses at their university, providing insights and analytics through ratings, reviews, and visual data representation.

## Core Requirements

### 1. User Management System
- **Student Registration/Authentication**: Secure user registration with university email validation
- **Profile Management**: Student profiles with academic information (major, year, courses taken)
- **Role-based Access**: Different access levels for students, administrators, and potentially professors
- **Session Management**: Secure login/logout with JWT tokens or similar authentication mechanism

### 2. Course & Professor Data Management
- **Course Catalog**: Comprehensive database of university courses (course codes, names, departments, credits)
- **Professor Directory**: Professor profiles with department affiliation, courses taught, and basic information
- **Course-Professor Relationships**: Many-to-many relationships between courses and professors
- **Academic Periods**: Support for different semesters/terms and academic years

### 3. Rating & Review System
- **Multi-dimensional Ratings**: Allow ratings on multiple criteria (teaching quality, difficulty, workload, clarity, helpfulness)
- **Written Reviews**: Text-based feedback with moderation capabilities
- **Rating Validation**: Ensure students can only rate courses/professors they've actually taken
- **Anonymous Options**: Allow anonymous reviews while maintaining data integrity
- **Edit/Delete Reviews**: Allow users to modify or remove their own reviews

### 4. Analytics & Insights Engine
- **Aggregate Statistics**: Calculate average ratings, distribution of scores, trend analysis
- **Comparative Analytics**: Compare professors within departments, courses across semesters
- **Sentiment Analysis**: Process written reviews for sentiment scoring
- **Visual Data Preparation**: Provide data in formats suitable for charts and graphs
- **Performance Metrics**: Track professor/course performance over time

### 5. Search & Discovery
- **Advanced Search**: Search by professor name, course code, department, rating thresholds
- **Filtering System**: Filter by difficulty level, workload, semester, rating ranges
- **Recommendation Engine**: Suggest courses/professors based on user preferences and past ratings
- **Trending Content**: Highlight highly-rated or recently reviewed courses/professors

## Technical Requirements

### Technology Stack
- **Java Version**: Java 21 (LTS) with modern language features
- **Framework**: Spring Boot 3.x with Spring Framework 6.x
- **Build Tool**: Maven with multi-module project structure
- **Database**: PostgreSQL with Spring Data JPA and Hibernate
- **Documentation**: SpringDoc OpenAPI 3 (Swagger UI integration)
- **Testing**: JUnit 5, Mockito, TestContainers for integration tests

### Architecture & Design Patterns
- **Layered Architecture**: Controller → Service → Repository → Entity layers
- **Domain-Driven Design**: Clear domain model separation with aggregates
- **RESTful API Design**: Well-structured endpoints following REST principles
- **Spring Boot Best Practices**: Auto-configuration, profiles, and externalized configuration
- **Database Design**: Properly normalized relational database with appropriate indexes
- **Scalable Architecture**: Design for growth with potential microservices consideration
- **Error Handling**: Comprehensive error responses with appropriate HTTP status codes and Spring Boot error handling
- **API Documentation**: SpringDoc OpenAPI 3 with detailed endpoint documentation

### Spring Boot Implementation Details

#### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/ratemyclass/
│   │       ├── RateMyClassApplication.java
│   │       ├── config/
│   │       │   ├── SecurityConfig.java
│   │       │   ├── JpaConfig.java
│   │       │   └── OpenApiConfig.java
│   │       ├── controller/
│   │       │   ├── AuthController.java
│   │       │   ├── CourseController.java
│   │       │   ├── ProfessorController.java
│   │       │   └── RatingController.java
│   │       ├── service/
│   │       │   ├── AuthService.java
│   │       │   ├── CourseService.java
│   │       │   ├── ProfessorService.java
│   │       │   └── RatingService.java
│   │       ├── repository/
│   │       │   ├── UserRepository.java
│   │       │   ├── CourseRepository.java
│   │       │   ├── ProfessorRepository.java
│   │       │   └── RatingRepository.java
│   │       ├── entity/
│   │       │   ├── User.java
│   │       │   ├── Course.java
│   │       │   ├── Professor.java
│   │       │   └── Rating.java
│   │       ├── dto/
│   │       │   ├── request/
│   │       │   └── response/
│   │       ├── exception/
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   └── CustomExceptions.java
│   │       └── util/
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── db/migration/
└── test/
```

#### Key Spring Boot Dependencies (pom.xml)
- `spring-boot-starter-web` - Web MVC framework
- `spring-boot-starter-data-jpa` - JPA and Hibernate
- `spring-boot-starter-security` - Security framework
- `spring-boot-starter-validation` - Bean validation
- `spring-boot-starter-cache` - Caching abstraction
- `spring-boot-starter-actuator` - Production monitoring
- `postgresql` - PostgreSQL JDBC driver
- `springdoc-openapi-starter-webmvc-ui` - OpenAPI 3 documentation
- `spring-boot-testcontainers` - Integration testing with containers

#### Entity Design with JPA
```java
@Entity
@Table(name = "professors")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Rating> ratings = new ArrayList<>();
    
    // Constructors, getters, setters, equals, hashCode
}
```

#### Repository Layer with Spring Data JPA
```java
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
    @Query("SELECT p FROM Professor p WHERE p.department.id = :deptId")
    List<Professor> findByDepartmentId(@Param("deptId") Long departmentId);
    
    @Query("SELECT p FROM Professor p JOIN p.ratings r GROUP BY p.id HAVING AVG(r.overallRating) >= :minRating")
    List<Professor> findByMinimumAverageRating(@Param("minRating") Double minRating);
    
    @Modifying
    @Query("UPDATE Professor p SET p.isActive = false WHERE p.id = :id")
    void deactivateProfessor(@Param("id") Long id);
}
```

#### Service Layer with Business Logic
```java
@Service
@Transactional
@Slf4j
public class RatingService {
    
    private final RatingRepository ratingRepository;
    private final ProfessorRepository professorRepository;
    private final CacheManager cacheManager;
    
    @Cacheable(value = "professorRatings", key = "#professorId")
    public ProfessorRatingDto getProfessorRatings(Long professorId) {
        // Business logic implementation
    }
    
    @CacheEvict(value = "professorRatings", key = "#rating.professor.id")
    public RatingDto submitRating(CreateRatingRequest request, String userEmail) {
        // Validation and persistence logic
    }
}
```

#### Controller Layer with Spring MVC
```java
@RestController
@RequestMapping("/api/v1/ratings")
@Validated
@Tag(name = "Ratings", description = "Rating and review management")
public class RatingController {
    
    private final RatingService ratingService;
    
    @PostMapping
    @Operation(summary = "Submit a new rating")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rating created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Rating already exists")
    })
    public ResponseEntity<RatingDto> submitRating(
            @Valid @RequestBody CreateRatingRequest request,
            Authentication authentication) {
        
        RatingDto rating = ratingService.submitRating(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }
    
    @GetMapping("/professor/{professorId}")
    @Operation(summary = "Get ratings for a professor")
    public ResponseEntity<ProfessorRatingDto> getProfessorRatings(
            @PathVariable @Positive Long professorId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        
        ProfessorRatingDto ratings = ratingService.getProfessorRatings(professorId, page, size);
        return ResponseEntity.ok(ratings);
    }
}
```

### Security & Privacy
- **Spring Security**: JWT-based authentication with role-based authorization
- **Data Protection**: Implement GDPR-compliant data handling with Spring Boot
- **Input Validation**: Bean validation with @Valid annotations and custom validators
- **Rate Limiting**: Spring Boot actuator with custom rate limiting filters
- **Review Moderation**: Content filtering for inappropriate language or content
- **Privacy Controls**: Allow users to control visibility of their reviews
- **CORS Configuration**: Proper Cross-Origin Resource Sharing setup
- **SQL Injection Prevention**: JPA/Hibernate parameter binding and query validation

### Performance & Caching
- **Spring Cache**: Redis or Caffeine for frequently accessed data (ratings, aggregates)
- **Database Optimization**: JPA query optimization with @Query annotations and proper indexing
- **Pagination**: Spring Data JPA pagination for large datasets
- **Response Times**: Optimize for fast response times (<200ms for most endpoints)
- **Connection Pooling**: HikariCP for efficient database connections
- **Async Processing**: @Async for background tasks and email notifications

### Data Management
- **Data Backup**: Regular automated backups with recovery procedures
- **Data Migration**: Scripts for database schema updates and data migrations
- **Analytics Storage**: Efficient storage and retrieval of historical rating data
- **Bulk Operations**: Support for importing course catalogs and professor data

## API Endpoints (Core Examples)

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout
- `GET /api/auth/profile` - Get user profile

### Courses & Professors
- `GET /api/courses` - List courses with filtering
- `GET /api/courses/{id}` - Get course details
- `GET /api/professors` - List professors with filtering
- `GET /api/professors/{id}` - Get professor details

### Ratings & Reviews
- `POST /api/ratings` - Submit a rating/review
- `GET /api/ratings/course/{id}` - Get ratings for a course
- `GET /api/ratings/professor/{id}` - Get ratings for a professor
- `PUT /api/ratings/{id}` - Update user's rating
- `DELETE /api/ratings/{id}` - Delete user's rating

### Analytics
- `GET /api/analytics/course/{id}` - Course analytics and insights
- `GET /api/analytics/professor/{id}` - Professor analytics and insights
- `GET /api/analytics/department/{id}` - Department-wide statistics

## Success Criteria
- ✅ Secure and scalable user authentication system
- ✅ Comprehensive rating system with data integrity
- ✅ Fast and accurate search functionality
- ✅ Real-time analytics and insights generation
- ✅ Robust data validation and security measures
- ✅ Performance optimization for concurrent users
- ✅ Complete API documentation with examples
- ✅ Comprehensive test coverage (unit, integration, API tests)

## Implementation Considerations
- **Spring Boot 3.x** with Java 21 virtual threads for improved performance
- **PostgreSQL** with proper database design and migrations using Flyway
- **Maven multi-module** structure for scalability and separation of concerns
- **Spring Profiles** for different environments (dev, test, prod)
- **Docker containerization** with Spring Boot optimized images
- **Spring Boot Actuator** for monitoring and health checks
- **Testing strategy**: Unit tests with @SpringBootTest, @WebMvcTest, @DataJpaTest
- **CI/CD pipeline** with Maven and Spring Boot deployment
- **Compliance** with university data policies and GDPR requirements