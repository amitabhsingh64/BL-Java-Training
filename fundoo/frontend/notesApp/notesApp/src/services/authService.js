import api from './api.js';

// --- LocalStorage helpers ---
export const getUser = () => JSON.parse(localStorage.getItem('user'));
export const setUser = (user) => localStorage.setItem('user', JSON.stringify(user));
export const clearUser = () => localStorage.removeItem('user');
export const isAuthenticated = () => !!localStorage.getItem('user');
export const redirectToDashboard = () => '/dashboard';
export const redirectToLogin = () => '/login';

// --- Auth API calls ---
export const login = (email, password) =>
    api.post('/api/users/login', { email, password }).then(r => r.data);

export const register = (data) =>
    api.post('/api/users/register', data).then(r => r.data);

export const forgotPassword = (email, newPassword) =>
    api.post('/api/users/forgot-password', { email, newPassword }).then(r => r.data);

export const sendOtp = (email) =>
    api.post('/api/otp/send', { email }).then(r => r.data);

export const sendSignupOtp = (email) =>
    api.post('/api/otp/send-signup', { email }).then(r => r.data);

export const verifyOtp = (email, otp) =>
    api.post('/api/otp/verify', { email, otp }).then(r => r.data);

export const sendLoginOtp = (email, password) =>
    api.post('/api/otp/send-login', { email, password }).then(r => r.data);

export const verifyLoginOtp = (email, otp) =>
    api.post('/api/otp/verify-login', { email, otp }).then(r => r.data);
