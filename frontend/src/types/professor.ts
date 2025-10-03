export interface Professor {
  id: number;
  firstName: string;
  lastName: string;
  title?: string;
  bio?: string;
  photoUrl?: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
  
  // Department info
  departmentId: number;
  departmentCode: string;
  departmentName: string;
  
  // Aggregated data
  courseCount?: number;
  ratingCount?: number;
  averageRating?: number;
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

export interface Course {
  id: number;
  code: string;
  name: string;
  description?: string;
  credits: number;
  active: boolean;
  departmentId: number;
  departmentCode?: string;
  departmentName?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Rating {
  id: number;
  overallRating: number;
  difficultyRating: number;
  wouldTakeAgain: boolean;
  comment?: string;
  createdAt: string;
  updatedAt: string;
  professorId: number;
  courseId: number;
  userId: number;
}

export interface ProfessorSearchFilters {
  departmentId?: number;
  minRating?: number;
  name?: string;
}

export interface PaginationParams {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: 'asc' | 'desc';
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  empty: boolean;
}