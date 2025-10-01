// Type definitions for the RateMyClass application

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  studentId: string;
  major?: string;
  graduationYear?: number;
  role: 'STUDENT' | 'PROFESSOR' | 'ADMIN';
  enabled: boolean;
  emailVerified: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface Department {
  id: number;
  code: string;
  name: string;
  description?: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface Professor {
  id: number;
  firstName: string;
  lastName: string;
  title?: string;
  bio?: string;
  photoUrl?: string;
  active: boolean;
  department: Department;
  createdAt: string;
  updatedAt: string;
}

export interface Course {
  id: number;
  code: string;
  name: string;
  description?: string;
  credits: number;
  active: boolean;
  department: Department;
  professors: Professor[];
  prerequisites: string[];
  createdAt: string;
  updatedAt: string;
}

export interface Rating {
  id: number;
  overallRating: number;
  teachingQuality?: number;
  difficulty?: number;
  workload?: number;
  clarity?: number;
  reviewText?: string;
  wouldTakeAgain: boolean;
  isAnonymous: boolean;
  semester?: string;
  year?: number;
  active: boolean;
  user: User;
  professor: Professor;
  course: Course;
  createdAt: string;
  updatedAt: string;
}

// DTOs for API requests and responses
export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  studentId: string;
  password: string;
  major?: string;
  graduationYear?: number;
}

export interface CreateRatingRequest {
  professorId: number;
  courseId: number;
  overallRating: number;
  teachingQuality?: number;
  difficulty?: number;
  workload?: number;
  clarity?: number;
  reviewText?: string;
  wouldTakeAgain: boolean;
  isAnonymous: boolean;
  semester?: string;
  year?: number;
}

export interface UpdateRatingRequest {
  overallRating?: number;
  teachingQuality?: number;
  difficulty?: number;
  workload?: number;
  clarity?: number;
  reviewText?: string;
  wouldTakeAgain?: boolean;
  isAnonymous?: boolean;
  semester?: string;
  year?: number;
}

// Analytics types
export interface RatingStatistics {
  averageRating: number;
  totalReviews: number;
  averageTeachingQuality: number;
  averageDifficulty: number;
  averageWorkload: number;
  averageClarity: number;
  wouldTakeAgainPercentage: number;
}

export interface ProfessorAnalytics extends RatingStatistics {
  professor: Professor;
  courseRatings: CourseRatingBreakdown[];
}

export interface CourseAnalytics extends RatingStatistics {
  course: Course;
  professorRatings: ProfessorRatingBreakdown[];
}

export interface CourseRatingBreakdown {
  course: Course;
  statistics: RatingStatistics;
}

export interface ProfessorRatingBreakdown {
  professor: Professor;
  statistics: RatingStatistics;
}

// Filter and search types
export interface SearchFilters {
  search?: string;
  departmentId?: number;
  minRating?: number;
  maxRating?: number;
  credits?: number;
}

export interface PaginationParams {
  page: number;
  size: number;
  sort?: string;
  direction?: 'ASC' | 'DESC';
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

// API Response types
export interface ApiResponse<T> {
  data: T;
  message?: string;
  timestamp: string;
}

export interface ErrorResponse {
  error: string;
  message: string;
  timestamp: string;
  path: string;
  status: number;
}

// Authentication types
export interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  error: string | null;
}

export interface JwtPayload {
  sub: string;
  email: string;
  role: string;
  exp: number;
  iat: number;
}

// Component Props types
export interface RatingFormData {
  professorId: number;
  courseId: number;
  overallRating: number;
  teachingQuality: number;
  difficulty: number;
  workload: number;
  clarity: number;
  reviewText: string;
  wouldTakeAgain: boolean;
  isAnonymous: boolean;
  semester: string;
  year: number;
}

export interface SearchBarProps {
  placeholder?: string;
  onSearch: (query: string) => void;
  onFilter?: (filters: SearchFilters) => void;
  loading?: boolean;
}

export interface RatingDisplayProps {
  rating: number;
  size?: 'small' | 'default' | 'large';
  showText?: boolean;
  precision?: number;
}

export interface FilterPanelProps {
  filters: SearchFilters;
  onFiltersChange: (filters: SearchFilters) => void;
  departments: Department[];
  loading?: boolean;
}

// Chart data types
export interface ChartDataPoint {
  name: string;
  value: number;
  label?: string;
}

export interface RatingDistribution {
  rating: number;
  count: number;
  percentage: number;
}

export interface TrendDataPoint {
  date: string;
  rating: number;
  count: number;
}