import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Layout } from 'antd';
import AppLayout from '@components/layout/AppLayout';
import HomePage from '@pages/Home/HomePage';
import LoginPage from '@pages/Auth/LoginPage';
import RegisterPage from '@pages/Auth/RegisterPage';
import ProfessorListPage from '@pages/Professor/ProfessorListPage';
import ProfessorProfilePage from '@pages/Professor/ProfessorProfilePage';
import CourseListPage from '@pages/Course/CourseListPage';
import CourseProfilePage from '@pages/Course/CourseProfilePage';
import SearchResultsPage from '@pages/Search/SearchResultsPage';
import DashboardPage from '@pages/Dashboard/DashboardPage';
import ProtectedRoute from '@components/auth/ProtectedRoute';
import { useAuth } from '@hooks/useAuth';

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
      {/* Public routes */}
      <Route path="/login" element={!isAuthenticated ? <LoginPage /> : <Navigate to="/dashboard" />} />
      <Route path="/register" element={!isAuthenticated ? <RegisterPage /> : <Navigate to="/dashboard" />} />
      
      {/* Protected routes with layout */}
      <Route path="/" element={<AppLayout />}>
        <Route index element={<HomePage />} />
        <Route path="dashboard" element={
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        } />
        <Route path="professors" element={<ProfessorListPage />} />
        <Route path="professors/:id" element={<ProfessorProfilePage />} />
        <Route path="courses" element={<CourseListPage />} />
        <Route path="courses/:id" element={<CourseProfilePage />} />
        <Route path="search" element={<SearchResultsPage />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Route>
    </Routes>
  );
};

export default App;