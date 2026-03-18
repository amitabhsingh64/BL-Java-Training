import React from 'react';
import { Navigate } from 'react-router-dom';
import { isAuthenticated, redirectToLogin } from '../services/authService.js';

const ProtectedRoute = ({ children }) => {
  if (!isAuthenticated()) {
    return <Navigate to={redirectToLogin()} replace />;
  }
  return children;
};

export default ProtectedRoute;