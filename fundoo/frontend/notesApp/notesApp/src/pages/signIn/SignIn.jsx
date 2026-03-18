import { useState, useRef } from "react";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import { sendLoginOtp, verifyLoginOtp, setUser } from "../../services/authService.js";
import {
  Box, Card, CardContent, TextField, Typography, Button, Link,
  IconButton, InputAdornment, Alert,
} from "@mui/material";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

export default function Login() {
  const navigate = useNavigate();
  const emailRef    = useRef();
  const passwordRef = useRef();
  const otpRef      = useRef();

  const [showPassword, setShowPassword] = useState(false);
  const [step, setStep]                 = useState(1); // 1 = credentials, 2 = otp
  const [email, setEmail]               = useState("");
  const [errors, setErrors]             = useState({});
  const [sending, setSending]           = useState(false);

  // Step 1: validate then send OTP
  const handleNext = async () => {
    const emailVal    = emailRef.current.value;
    const passwordVal = passwordRef.current.value;
    const newErrors   = {};

    if (!emailVal || !emailVal.includes("@"))  newErrors.email    = "Enter a valid email address";
    if (!passwordVal || passwordVal.length < 8) newErrors.password = "Password must be at least 8 characters";

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    setSending(true);
    try {
      await sendLoginOtp(emailVal, passwordVal);
      setEmail(emailVal);
      setErrors({});
      setStep(2);
    } catch (err) {
      const msg = err.response?.data || "Could not connect to the server.";
      if (err.response?.status === 401 || msg.toLowerCase().includes("invalid")) {
        setErrors({ general: "Invalid email or password" });
      } else {
        setErrors({ general: msg });
      }
    } finally {
      setSending(false);
    }
  };

  // Step 2: verify OTP and log in
  const handleVerify = async () => {
    const otp = otpRef.current.value.trim();
    if (!otp) {
      setErrors({ otp: "Enter the OTP" });
      return;
    }
    try {
      const user = await verifyLoginOtp(email, otp);
      setUser(user);
      navigate("/");
    } catch (err) {
      setErrors({ otp: err.response?.data || "Invalid or expired OTP" });
    }
  };

  return (
    <Box sx={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center", bgcolor: "#f8f9fa" }}>
      <Card sx={{ width: 420, p: 2 }}>
        <CardContent>
          <Typography variant="h5" fontWeight={600} color="#1a73e8" mb={1} align="center">
            Fundoo Notes
          </Typography>
          <Typography variant="h4" fontWeight={400} mb={1} align="center">
            Sign in
          </Typography>
          <Typography variant="body1" color="text.secondary" mb={3} align="center">
            {step === 1 ? "to continue to Fundoo" : `Enter the OTP sent to ${email}`}
          </Typography>

          {errors.general && <Alert severity="error" sx={{ mb: 2 }}>{errors.general}</Alert>}

          {/* Step 1: email + password */}
          {step === 1 && (
            <>
              <TextField
                label="Email" fullWidth sx={{ mb: 2 }}
                inputRef={emailRef} error={!!errors.email} helperText={errors.email}
              />
              <TextField
                label="Password" type={showPassword ? "text" : "password"}
                fullWidth sx={{ mt: 1 }}
                inputRef={passwordRef} error={!!errors.password} helperText={errors.password}
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton onClick={() => setShowPassword(p => !p)} edge="end">
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
              <Link component={RouterLink} to="/forgot-password" underline="none" fontWeight={500} sx={{ mt: 1, display: "block" }}>
                Forgot password?
              </Link>
              <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mt: 4 }}>
                <Link component={RouterLink} to="/signup" underline="none" fontWeight={500}>
                  Create account
                </Link>
                <Button
                  variant="contained"
                  sx={{ bgcolor: "#1a73e8", textTransform: "none", px: 4 }}
                  onClick={handleNext}
                  disabled={sending}
                >
                  {sending ? "Sending OTP..." : "Next"}
                </Button>
              </Box>
            </>
          )}

          {/* Step 2: OTP */}
          {step === 2 && (
            <>
              <TextField
                label="Enter OTP" fullWidth sx={{ mb: 2 }}
                inputRef={otpRef} error={!!errors.otp} helperText={errors.otp}
                inputProps={{ maxLength: 6 }}
              />
              <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mt: 2 }}>
                <Button
                  variant="text"
                  sx={{ textTransform: "none", color: "#1a73e8" }}
                  onClick={() => { setStep(1); setErrors({}); }}
                >
                  Resend OTP
                </Button>
                <Button
                  variant="contained"
                  sx={{ bgcolor: "#1a73e8", textTransform: "none", px: 4 }}
                  onClick={handleVerify}
                >
                  Verify & Sign in
                </Button>
              </Box>
            </>
          )}
        </CardContent>
      </Card>
    </Box>
  );
}
