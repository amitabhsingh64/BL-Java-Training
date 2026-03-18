import { useRef, useState } from "react";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import { sendOtp, verifyOtp, forgotPassword } from "../../services/authService.js";
import {
  Box, Card, CardContent, TextField, Typography, Button, Link, Alert,
} from "@mui/material";

export default function ForgotPassword() {
  const navigate = useNavigate();
  const emailRef        = useRef();
  const otpRef          = useRef();
  const newPasswordRef  = useRef();
  const confirmPasswordRef = useRef();

  const [step, setStep]           = useState(1); // 1 = email, 2 = otp, 3 = new password
  const [email, setEmail]         = useState("");
  const [errors, setErrors]       = useState({});
  const [successMsg, setSuccessMsg] = useState("");
  const [sending, setSending]     = useState(false);

  // Step 1: send OTP
  const handleSendOtp = async () => {
    const val = emailRef.current.value.trim();
    if (!val.match(/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/)) {
      setErrors({ email: "Enter a valid email address" });
      return;
    }
    setSending(true);
    try {
      await sendOtp(val);
      setEmail(val);
      setErrors({});
      setSuccessMsg("OTP sent to " + val);
      setStep(2);
    } catch (err) {
      setErrors({ email: err.response?.data || "Failed to send OTP" });
    } finally {
      setSending(false);
    }
  };

  // Step 2: verify OTP
  const handleVerifyOtp = async () => {
    const otp = otpRef.current.value.trim();
    if (!otp) {
      setErrors({ otp: "Enter the OTP" });
      return;
    }
    try {
      await verifyOtp(email, otp);
      setErrors({});
      setSuccessMsg("OTP verified. Set your new password.");
      setStep(3);
    } catch (err) {
      setErrors({ otp: err.response?.data || "Invalid or expired OTP" });
    }
  };

  // Step 3: reset password
  const handleResetPassword = async () => {
    const newPassword     = newPasswordRef.current.value;
    const confirmPassword = confirmPasswordRef.current.value;
    const newErrors = {};

    if (newPassword.length < 8) newErrors.newPassword = "Password must be at least 8 characters";
    if (newPassword !== confirmPassword) newErrors.confirmPassword = "Passwords do not match";

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }
    try {
      await forgotPassword(email, newPassword);
      setSuccessMsg("Password reset successfully! Redirecting to login...");
      setErrors({});
      setTimeout(() => navigate("/login"), 2000);
    } catch (err) {
      setErrors({ general: err.response?.data || "Could not reset password." });
    }
  };

  return (
    <Box sx={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center", bgcolor: "#f8f9fa" }}>
      <Card sx={{ width: 420, p: 2 }}>
        <CardContent>
          <Typography variant="h5" fontWeight={600} color="#1a73e8" mb={1} align="center">
            Fundoo Notes
          </Typography>
          <Typography variant="h5" fontWeight={400} mb={1} align="center">
            Reset your password
          </Typography>
          <Typography variant="body2" color="text.secondary" mb={3} align="center">
            {step === 1 && "Enter your registered email to receive an OTP"}
            {step === 2 && "Enter the 6-digit OTP sent to " + email}
            {step === 3 && "Enter your new password"}
          </Typography>

          {successMsg && <Alert severity="success" sx={{ mb: 2 }}>{successMsg}</Alert>}
          {errors.general && <Alert severity="error" sx={{ mb: 2 }}>{errors.general}</Alert>}

          {/* Step 1: Email */}
          {step === 1 && (
            <>
              <TextField
                label="Email"
                fullWidth
                sx={{ mb: 2 }}
                inputRef={emailRef}
                error={!!errors.email}
                helperText={errors.email}
              />
              <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mt: 2 }}>
                <Link component={RouterLink} to="/login" underline="none" fontWeight={500}>
                  Back to Sign in
                </Link>
                <Button
                  variant="contained"
                  sx={{ bgcolor: "#1a73e8", textTransform: "none", px: 4 }}
                  onClick={handleSendOtp}
                  disabled={sending}
                >
                  {sending ? "Sending..." : "Send OTP"}
                </Button>
              </Box>
            </>
          )}

          {/* Step 2: OTP */}
          {step === 2 && (
            <>
              <TextField
                label="Enter OTP"
                fullWidth
                sx={{ mb: 2 }}
                inputRef={otpRef}
                error={!!errors.otp}
                helperText={errors.otp}
                inputProps={{ maxLength: 6 }}
              />
              <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mt: 2 }}>
                <Button
                  variant="text"
                  sx={{ textTransform: "none", color: "#1a73e8" }}
                  onClick={() => { setStep(1); setSuccessMsg(""); setErrors({}); }}
                >
                  Resend OTP
                </Button>
                <Button
                  variant="contained"
                  sx={{ bgcolor: "#1a73e8", textTransform: "none", px: 4 }}
                  onClick={handleVerifyOtp}
                >
                  Verify OTP
                </Button>
              </Box>
            </>
          )}

          {/* Step 3: New password */}
          {step === 3 && (
            <>
              <TextField
                label="New Password"
                type="password"
                fullWidth
                sx={{ mb: 2 }}
                inputRef={newPasswordRef}
                error={!!errors.newPassword}
                helperText={errors.newPassword}
              />
              <TextField
                label="Confirm New Password"
                type="password"
                fullWidth
                inputRef={confirmPasswordRef}
                error={!!errors.confirmPassword}
                helperText={errors.confirmPassword}
              />
              <Box sx={{ display: "flex", justifyContent: "flex-end", mt: 4 }}>
                <Button
                  variant="contained"
                  sx={{ bgcolor: "#1a73e8", textTransform: "none", px: 4 }}
                  onClick={handleResetPassword}
                >
                  Reset Password
                </Button>
              </Box>
            </>
          )}
        </CardContent>
      </Card>
    </Box>
  );
}
