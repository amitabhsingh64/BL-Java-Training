import React from 'react';
import { Navigate } from 'react-router-dom';
import { isAuthenticated, redirectToDashboard } from '../services/authService.js';

const AuthRoute = ({ children }) => {
  if (isAuthenticated()) {
    return <Navigate to={redirectToDashboard()} replace />;
  }
  return children;
};

export default AuthRoute;