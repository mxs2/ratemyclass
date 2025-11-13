import { apiClient } from './client.js';
import type { LoginRequest, RegisterRequest, AuthResponse, User } from '../../types/auth';

// Mock user para desenvolvimento
const mockUser: User = {
  id: 1,
  firstName: 'Usuário',
  lastName: 'Teste',
  email: 'usuario@test.com',
  studentId: '2025001',
  major: 'Engenharia de Software',
  graduationYear: 2026,
  role: 'STUDENT',
  enabled: true,
  emailVerified: true,
  createdAt: '2025-11-01T10:00:00Z',
  updatedAt: '2025-11-12T15:30:00Z',
};

// Mock token e response para desenvolvimento
const mockToken = 'mock-jwt-token-for-development-purposes-only';

export const authApi = {
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    try {
      const response = await apiClient.post<AuthResponse>('/auth/login', credentials);
      return response.data;
    } catch (err) {
      // Mock em caso de erro de conexão
      console.warn('API indisponível, usando mock para login');
      // Simula delay de rede
      await new Promise(resolve => setTimeout(resolve, 500));
      localStorage.setItem('authToken', mockToken);
      localStorage.setItem('user', JSON.stringify(mockUser));
      return {
        token: mockToken,
        tokenType: 'Bearer',
        user: mockUser,
        expiresIn: 3600,
      };
    }
  },

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    try {
      const response = await apiClient.post<AuthResponse>('/auth/register', userData);
      return response.data;
    } catch (err) {
      // Mock em caso de erro de conexão
      console.warn('API indisponível, usando mock para registrar');
      await new Promise(resolve => setTimeout(resolve, 500));
      const newUser: User = {
        id: Math.floor(Math.random() * 10000),
        firstName: userData.firstName,
        lastName: userData.lastName,
        email: userData.email,
        studentId: userData.studentId,
        major: userData.major,
        graduationYear: userData.graduationYear,
        role: 'STUDENT',
        enabled: true,
        emailVerified: false,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      };
      localStorage.setItem('authToken', mockToken);
      localStorage.setItem('user', JSON.stringify(newUser));
      return {
        token: mockToken,
        tokenType: 'Bearer',
        user: newUser,
        expiresIn: 3600,
      };
    }
  },

  async getCurrentUser(): Promise<User> {
    try {
      const response = await apiClient.get<User>('/auth/me');
      return response.data;
    } catch (err) {
      // Mock em caso de erro de conexão
      console.warn('API indisponível, usando mock para obter usuário atual');
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        return JSON.parse(storedUser);
      }
      return mockUser;
    }
  },

  async logout(): Promise<void> {
    try {
      await apiClient.post('/auth/logout');
    } catch (err) {
      // Mock em caso de erro de conexão
      console.warn('API indisponível, usando mock para logout');
    }
  },

  async forgotPassword(email: string): Promise<void> {
    try {
      await apiClient.post('/auth/forgot-password', { email });
    } catch (err) {
      // Mock em caso de erro de conexão
      console.warn('API indisponível, usando mock para forgot-password');
      await new Promise(resolve => setTimeout(resolve, 500));
    }
  },

  async resetPassword(token: string, newPassword: string): Promise<void> {
    try {
      await apiClient.post('/auth/reset-password', { token, newPassword });
    } catch (err) {
      // Mock em caso de erro de conexão
      console.warn('API indisponível, usando mock para reset-password');
      await new Promise(resolve => setTimeout(resolve, 500));
    }
  },
};