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

export interface AuthResponse {
  token: string;
  tokenType: string;
  user: User;
  expiresIn: number;
}

export interface TokenPayload {
  sub: string;
  email: string;
  role: string;
  exp: number;
  iat: number;
}