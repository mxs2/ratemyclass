import axios, { AxiosInstance, AxiosResponse, AxiosError, InternalAxiosRequestConfig } from 'axios';

// Get API URL from Vite environment variables
const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

console.log('🌐 API Base URL:', API_URL);

// Create axios instance with base configuration
export const apiClient: AxiosInstance = axios.create({
  baseURL: API_URL,
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: false,
});

// Track if we're already redirecting to avoid multiple redirects
let isRedirecting = false;

// Request interceptor to add auth token
apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('authToken');
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle errors
apiClient.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  async (error: AxiosError) => {
    const originalRequest = error.config;

    // Handle 401 errors (unauthorized)
    if (error.response?.status === 401 && originalRequest && !isRedirecting) {
      isRedirecting = true;
      
      // Remove invalid token and user data
      localStorage.removeItem('authToken');
      localStorage.removeItem('user');
      
      // Redirect to login page with return URL
      const currentPath = window.location.pathname;
      const returnUrl = currentPath !== '/login' && currentPath !== '/register' 
        ? `?returnUrl=${encodeURIComponent(currentPath)}`
        : '';
      
      console.warn('🔒 Session expired. Redirecting to login...');
      window.location.href = `/login${returnUrl}`;
      
      return Promise.reject(new Error('Session expired. Please log in again.'));
    }

    // Handle network errors
    if (!error.response) {
      console.error('❌ Network error:', error.message);
      const message = 'Network error. Please check your connection and ensure the backend is running on port 8080.';
      return Promise.reject(new Error(message));
    }

    // Handle validation errors (let caller handle field-specific errors)
    if (error.response?.status === 400) {
      const data: any = error.response.data;
      if (data?.fieldErrors) {
        return Promise.reject(error);
      }
    }

    // Handle other HTTP errors with better messages
    const data: any = error.response?.data;
    const errorMessage = data?.message || data?.error || error.response.statusText || 'An unexpected error occurred';
    
    console.error('❌ API Error:', {
      status: error.response?.status,
      message: errorMessage,
      url: originalRequest?.url,
    });

    return Promise.reject(new Error(errorMessage));
  }
);

export default apiClient;