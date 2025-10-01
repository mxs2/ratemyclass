import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Layout } from 'antd';
import AppLayout from './components/layout/AppLayout';
import HomePage from './pages/Home/HomePage';
import LoginPage from './pages/Auth/LoginPage';
import RegisterPage from './pages/Auth/RegisterPage';
import ProfessorListPage from './pages/Professor/ProfessorListPage';
import ProfessorProfilePage from './pages/Professor/ProfessorProfilePage';
import CourseListPage from './pages/Course/CourseListPage';
import CourseProfilePage from './pages/Course/CourseProfilePage';
import SearchResultsPage from './pages/Search/SearchResultsPage';
import DashboardPage from './pages/Dashboard/DashboardPage';
import ProtectedRoute from './components/auth/ProtectedRoute';
import { useAuth } from './hooks/useAuth';

const App: React.FC = () => {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return (
      <Layout style={{ minHeight: '100vh', justifyContent: 'center', alignItems: 'center' }}>
        <div>Loading...</div>
      </Layout>
    );
  }

  return (
    <Routes>
      {/* Public routes without layout */}
      <Route path="/login" element={!isAuthenticated ? <LoginPage /> : <Navigate to="/dashboard" />} />
      <Route path="/register" element={!isAuthenticated ? <RegisterPage /> : <Navigate to="/dashboard" />} />
      
      {/* Routes with layout */}
      <Route path="/" element={
        <AppLayout>
          <HomePage />
        </AppLayout>
      } />
      <Route path="/dashboard" element={
        <AppLayout>
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        </AppLayout>
      } />
      <Route path="/professors" element={
        <AppLayout>
          <ProfessorListPage />
        </AppLayout>
      } />
      <Route path="/professors/:id" element={
        <AppLayout>
          <ProfessorProfilePage />
        </AppLayout>
      } />
      <Route path="/courses" element={
        <AppLayout>
          <CourseListPage />
        </AppLayout>
      } />
      <Route path="/courses/:id" element={
        <AppLayout>
          <CourseProfilePage />
        </AppLayout>
      } />
      <Route path="/search" element={
        <AppLayout>
          <SearchResultsPage />
        </AppLayout>
      } />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default App;