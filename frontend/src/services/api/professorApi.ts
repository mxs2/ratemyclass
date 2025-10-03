import { apiClient } from './client';
import { 
  Professor, 
  ProfessorSearchFilters, 
  PaginationParams, 
  PaginatedResponse 
} from '../../types/professor';

class ProfessorApi {
  private basePath = '/professors';

  /**
   * Get all professors with pagination
   */
  async getAllProfessors(params: PaginationParams = {}): Promise<PaginatedResponse<Professor>> {
    const queryParams = new URLSearchParams();
    
    if (params.page !== undefined) queryParams.append('page', params.page.toString());
    if (params.size !== undefined) queryParams.append('size', params.size.toString());
    if (params.sortBy) queryParams.append('sortBy', params.sortBy);
    if (params.sortDir) queryParams.append('sortDir', params.sortDir);

    const response = await apiClient.get<PaginatedResponse<Professor>>(
      `${this.basePath}?${queryParams.toString()}`
    );
    return response.data;
  }

  /**
   * Get professor by ID
   */
  async getProfessorById(id: number): Promise<Professor> {
    const response = await apiClient.get<Professor>(`${this.basePath}/${id}`);
    return response.data;
  }

  /**
   * Search professors by name
   */
  async searchProfessors(
    query: string, 
    params: PaginationParams = {}
  ): Promise<PaginatedResponse<Professor>> {
    const queryParams = new URLSearchParams();
    queryParams.append('q', query);
    
    if (params.page !== undefined) queryParams.append('page', params.page.toString());
    if (params.size !== undefined) queryParams.append('size', params.size.toString());
    if (params.sortBy) queryParams.append('sortBy', params.sortBy);
    if (params.sortDir) queryParams.append('sortDir', params.sortDir);

    const response = await apiClient.get<PaginatedResponse<Professor>>(
      `${this.basePath}/search?${queryParams.toString()}`
    );
    return response.data;
  }

  /**
   * Get professors by department
   */
  async getProfessorsByDepartment(
    departmentCode: string, 
    params: PaginationParams = {}
  ): Promise<PaginatedResponse<Professor>> {
    const queryParams = new URLSearchParams();
    
    if (params.page !== undefined) queryParams.append('page', params.page.toString());
    if (params.size !== undefined) queryParams.append('size', params.size.toString());
    if (params.sortBy) queryParams.append('sortBy', params.sortBy);
    if (params.sortDir) queryParams.append('sortDir', params.sortDir);

    const response = await apiClient.get<PaginatedResponse<Professor>>(
      `${this.basePath}/department/${departmentCode}?${queryParams.toString()}`
    );
    return response.data;
  }

  /**
   * Get top rated professors
   */
  async getTopRatedProfessors(params: PaginationParams = {}): Promise<PaginatedResponse<Professor>> {
    const queryParams = new URLSearchParams();
    
    if (params.page !== undefined) queryParams.append('page', params.page.toString());
    if (params.size !== undefined) queryParams.append('size', params.size.toString());

    const response = await apiClient.get<PaginatedResponse<Professor>>(
      `${this.basePath}/top-rated?${queryParams.toString()}`
    );
    return response.data;
  }

  /**
   * Get professors by course
   */
  async getProfessorsByCourse(courseId: number): Promise<Professor[]> {
    const response = await apiClient.get<Professor[]>(`${this.basePath}/course/${courseId}`);
    return response.data;
  }

  /**
   * Advanced search with filters
   */
  async searchWithFilters(
    filters: ProfessorSearchFilters, 
    params: PaginationParams = {}
  ): Promise<PaginatedResponse<Professor>> {
    const queryParams = new URLSearchParams();
    
    if (filters.departmentId !== undefined) {
      queryParams.append('departmentId', filters.departmentId.toString());
    }
    if (filters.minRating !== undefined) {
      queryParams.append('minRating', filters.minRating.toString());
    }
    if (filters.name) {
      queryParams.append('name', filters.name);
    }
    
    if (params.page !== undefined) queryParams.append('page', params.page.toString());
    if (params.size !== undefined) queryParams.append('size', params.size.toString());
    if (params.sortBy) queryParams.append('sortBy', params.sortBy);
    if (params.sortDir) queryParams.append('sortDir', params.sortDir);

    const response = await apiClient.get<PaginatedResponse<Professor>>(
      `${this.basePath}/filter?${queryParams.toString()}`
    );
    return response.data;
  }
}

// Export singleton instance
export const professorApi = new ProfessorApi();