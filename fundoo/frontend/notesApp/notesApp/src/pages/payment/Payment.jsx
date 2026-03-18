import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { loadStripe } from "@stripe/stripe-js";
import { Elements, CardElement, useStripe, useElements } from "@stripe/react-stripe-js";
import {
  Box, Card, CardContent, Typography, Button, Alert, CircularProgress, Divider,
} from "@mui/material";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import { getUser, setUser } from "../../services/authService.js";
import { createPaymentIntent } from "../../services/paymentService.js";

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);

const PLAN = {
  name: "Fundoo Premium",
  price: 999,        // cents
  currency: "usd",
  displayPrice: "$9.99",
  features: ["Unlimited notes", "Priority support", "Custom backgrounds", "Advanced search"],
};

// ── Inner form (must be inside <Elements>) ───────────────────────────────────
function CheckoutForm({ clientSecret }) {
  const stripe   = useStripe();
  const elements = useElements();
  const navigate = useNavigate();

  const [loading, setLoading]   = useState(false);
  const [error, setError]       = useState("");
  const [success, setSuccess]   = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!stripe || !elements) return;

    setLoading(true);
    setError("");

    const { error: stripeError, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
      payment_method: { card: elements.getElement(CardElement) },
    });

    setLoading(false);

    if (stripeError) {
      setError(stripeError.message);
    } else if (paymentIntent.status === "succeeded") {
      // Update stored user so header reflects premium status immediately
      const user = getUser();
      if (user) setUser({ ...user, isPremium: true });
      setSuccess(true);
      setTimeout(() => navigate("/dashboard"), 3000);
    }
  };

  if (success) {
    return (
      <Box sx={{ textAlign: "center", py: 3 }}>
        <CheckCircleOutlineIcon sx={{ fontSize: 60, color: "#34a853", mb: 2 }} />
        <Typography variant="h6" fontWeight={600}>Payment successful!</Typography>
        <Typography variant="body2" color="text.secondary" mt={1}>
          Welcome to Fundoo Premium. Redirecting to dashboard...
        </Typography>
      </Box>
    );
  }

  return (
    <form onSubmit={handleSubmit}>
      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      <Typography variant="body2" color="text.secondary" mb={1}>
        Card details
      </Typography>

      {/* Stripe CardElement */}
      <Box sx={{
        border: "1px solid #dadce0", borderRadius: 1, px: 2, py: 1.5, mb: 3,
        "&:focus-within": { borderColor: "#1a73e8", boxShadow: "0 0 0 2px rgba(26,115,232,0.2)" },
      }}>
        <CardElement options={{
          style: {
            base: { fontSize: "16px", color: "#424770", "::placeholder": { color: "#aab7c4" } },
            invalid: { color: "#e53935" },
          },
        }} />
      </Box>

      <Typography variant="caption" color="text.secondary" display="block" mb={2}>
        Use test card: <strong>4242 4242 4242 4242</strong> · Any future date · Any CVC
      </Typography>

      <Button
        type="submit"
        variant="contained"
        fullWidth
        disabled={!stripe || loading}
        sx={{ bgcolor: "#1a73e8", textTransform: "none", py: 1.5, fontSize: "1rem" }}
      >
        {loading ? <CircularProgress size={22} color="inherit" /> : `Pay ${PLAN.displayPrice}`}
      </Button>
    </form>
  );
}

// ── Page wrapper ─────────────────────────────────────────────────────────────
export default function Payment() {
  const navigate = useNavigate();
  const user = getUser();

  const [clientSecret, setClientSecret] = useState("");
  const [initError, setInitError]       = useState("");

  useEffect(() => {
    createPaymentIntent(user.id, PLAN.price, PLAN.currency)
      .then(data => setClientSecret(data.clientSecret))
      .catch(() => setInitError("Failed to initialise payment. Please try again."));
  }, []);

  return (
    <Box sx={{ minHeight: "100vh", bgcolor: "#f8f9fa", display: "flex", alignItems: "center", justifyContent: "center", p: 2 }}>
      <Card sx={{ width: "100%", maxWidth: 480, p: 2 }}>
        <CardContent>
          <Typography variant="h5" fontWeight={600} color="#1a73e8" align="center" mb={0.5}>
            Fundoo Notes
          </Typography>
          <Typography variant="h6" fontWeight={400} align="center" mb={3}>
            Upgrade to Premium
          </Typography>

          {/* Plan summary */}
          <Box sx={{ bgcolor: "#f1f8ff", borderRadius: 2, p: 2, mb: 3 }}>
            <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mb: 1 }}>
              <Typography fontWeight={600}>{PLAN.name}</Typography>
              <Typography fontWeight={700} color="#1a73e8">{PLAN.displayPrice}<Typography component="span" variant="body2" color="text.secondary">/month</Typography></Typography>
            </Box>
            <Divider sx={{ mb: 1 }} />
            {PLAN.features.map(f => (
              <Box key={f} sx={{ display: "flex", alignItems: "center", gap: 1, mt: 0.5 }}>
                <CheckCircleOutlineIcon sx={{ fontSize: 16, color: "#34a853" }} />
                <Typography variant="body2">{f}</Typography>
              </Box>
            ))}
          </Box>

          {initError && <Alert severity="error" sx={{ mb: 2 }}>{initError}</Alert>}

          {!clientSecret && !initError && (
            <Box sx={{ display: "flex", justifyContent: "center", py: 3 }}>
              <CircularProgress />
            </Box>
          )}

          {clientSecret && (
            <Elements stripe={stripePromise}>
              <CheckoutForm clientSecret={clientSecret} />
            </Elements>
          )}

          <Button
            variant="text" fullWidth
            sx={{ mt: 2, textTransform: "none", color: "text.secondary" }}
            onClick={() => navigate("/dashboard")}
          >
            Back to dashboard
          </Button>
        </CardContent>
      </Card>
    </Box>
  );
}
