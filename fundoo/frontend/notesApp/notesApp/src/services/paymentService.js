import api from './api.js';

export const createPaymentIntent = (userId, amount, currency) =>
    api.post('/api/stripe/payment-intent', { userId, amount, currency }).then(r => r.data);

export const cancelSubscriptionWithOtp = (userId, otp) =>
    api.post('/api/stripe/cancel-with-otp', { userId, otp }).then(r => r.data);
