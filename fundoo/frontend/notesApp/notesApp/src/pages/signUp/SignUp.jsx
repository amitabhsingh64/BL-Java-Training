import React, { useRef, useState } from "react";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import {
  Box, Card, CardContent, TextField, Typography, Button, Link, Alert,
} from "@mui/material";
import shield from "../../assets/img.jpeg";
import { register, sendSignupOtp, verifyOtp } from "../../services/authService.js";

const Signup = () => {
  const navigate = useNavigate();
  const firstNameRef       = useRef();
  const lastNameRef        = useRef();
  const emailRef           = useRef();
  const passwordRef        = useRef();
  const confirmPasswordRef = useRef();
  const otpRef             = useRef();

  const [step, setStep]       = useState(1); // 1 = fill form, 2 = verify OTP
  const [formData, setFormData] = useState(null);
  const [errors, setErrors]   = useState({});
  const [successMsg, setSuccessMsg] = useState("");
  const [sending, setSending] = useState(false);

  // Step 1: validate form and send OTP
  const handleNext = async () => {
    const firstName       = firstNameRef.current.value;
    const lastName        = lastNameRef.current.value;
    const email           = emailRef.current.value;
    const password        = passwordRef.current.value;
    const confirmPassword = confirmPasswordRef.current.value;

    const newErrors = {};
    if (!firstName.match(/^[A-Za-z]{2,}$/))         newErrors.firstName = "Enter a valid first name";
    if (!lastName.match(/^[A-Za-z]{2,}$/))           newErrors.lastName  = "Enter a valid last name";
    if (!email.match(/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/)) newErrors.email = "Enter a valid email address";
    if (password.length < 8)                          newErrors.password  = "Password must be at least 8 characters";
    if (password !== confirmPassword)                 newErrors.confirmPassword = "Passwords do not match";

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    setSending(true);
    try {
      await sendSignupOtp(email);
      setFormData({ firstName, lastName, email, password });
      setErrors({});
      setSuccessMsg("OTP sent to " + email);
      setStep(2);
    } catch (err) {
      const msg = err.response?.data || "Failed to send OTP";
      if (msg.toLowerCase().includes("email")) {
        setErrors({ email: msg });
      } else {
        setErrors({ general: msg });
      }
    } finally {
      setSending(false);
    }
  };

  // Step 2: verify OTP then register
  const handleVerifyAndRegister = async () => {
    const otp = otpRef.current.value.trim();
    if (!otp) {
      setErrors({ otp: "Enter the OTP" });
      return;
    }
    try {
      await verifyOtp(formData.email, otp);
      await register(formData);
      navigate("/login");
    } catch (err) {
      const msg = err.response?.data || "Verification failed";
      if (msg.toLowerCase().includes("otp") || msg.toLowerCase().includes("invalid") || msg.toLowerCase().includes("expired")) {
        setErrors({ otp: msg });
      } else {
        setErrors({ general: msg });
      }
    }
  };

  return (
    <Box sx={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center", bgcolor: "#f8f9fa" }}>
      <Card sx={{ width: 900, p: 2 }}>
        <CardContent>
          <Box sx={{ display: "flex" }}>

            {/* Left Content */}
            <Box sx={{ flex: 1, pr: 4 }}>
              <Typography variant="h5" fontWeight={600} color="#1a73e8" mb={1}>
                Fundoo
              </Typography>
              <Typography fontWeight={500} mb={3} style={{ fontSize: "2rem" }}>
                {step === 1 ? "Create your Fundoo Account" : "Verify your email"}
              </Typography>

              {errors.general && <Alert severity="error" sx={{ mb: 2 }}>{errors.general}</Alert>}
              {successMsg     && <Alert severity="success" sx={{ mb: 2 }}>{successMsg}</Alert>}

              {/* Step 1: Registration form */}
              {step === 1 && (
                <>
                  <Box sx={{ display: "flex", gap: 2, mb: 2 }}>
                    <TextField label="First name" fullWidth inputRef={firstNameRef} error={!!errors.firstName} helperText={errors.firstName} />
                    <TextField label="Last name"  fullWidth inputRef={lastNameRef}  error={!!errors.lastName}  helperText={errors.lastName} />
                  </Box>

                  <TextField
                    label="Your email address" fullWidth sx={{ mb: 1 }}
                    inputRef={emailRef} error={!!errors.email} helperText={errors.email}
                  />

                  <Box sx={{ display: "flex", gap: 2, mt: 3 }}>
                    <TextField label="Password" type="password" fullWidth inputRef={passwordRef} error={!!errors.password} helperText={errors.password} />
                    <TextField label="Confirm"  type="password" fullWidth inputRef={confirmPasswordRef} error={!!errors.confirmPassword} helperText={errors.confirmPassword} />
                  </Box>

                  <Typography variant="body2" color="text.secondary" mt={1}>
                    Use 8 or more characters with a mix of letters, numbers & symbols
                  </Typography>

                  <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mt: 4 }}>
                    <Link component={RouterLink} to="/login" underline="none" fontWeight={500}>
                      Sign in instead
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

              {/* Step 2: OTP verification */}
              {step === 2 && (
                <>
                  <Typography variant="body2" color="text.secondary" mb={3}>
                    Enter the 6-digit OTP sent to <strong>{formData?.email}</strong>
                  </Typography>

                  <TextField
                    label="Enter OTP" fullWidth sx={{ mb: 2 }}
                    inputRef={otpRef} error={!!errors.otp} helperText={errors.otp}
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
                      onClick={handleVerifyAndRegister}
                    >
                      Verify & Create Account
                    </Button>
                  </Box>
                </>
              )}
            </Box>

            {/* Right Content */}
            <Box sx={{ flex: 1, display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center" }}>
              <img src={shield} alt="Security Illustration" style={{ width: "80%", maxWidth: "300px" }} />
              <Typography variant="body1" align="center" mt={2}>
                One account for all your Notes needs.
              </Typography>
            </Box>

          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Signup;
