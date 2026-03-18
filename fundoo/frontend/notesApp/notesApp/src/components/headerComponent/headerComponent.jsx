import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import {
  Tooltip, Button, Chip, Dialog, DialogTitle, DialogContent,
  DialogActions, TextField, Typography, Alert, CircularProgress, Box,
} from "@mui/material";
import IconButton from "@mui/material/IconButton";
import InputBase from "@mui/material/InputBase";
import AccountMenu from "../accountMenuCard/accountMenuCard.jsx";
import AppsIcon from "@mui/icons-material/Apps";
import MenuIcon from "@mui/icons-material/Menu";
import SearchIcon from "@mui/icons-material/Search";
import RefreshIcon from "@mui/icons-material/Refresh";
import ViewAgendaOutlinedIcon from "@mui/icons-material/ViewAgendaOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import AccountCircle from "@mui/icons-material/AccountCircle";
import WorkspacePremiumOutlinedIcon from "@mui/icons-material/WorkspacePremiumOutlined";
import { getUser, setUser, sendOtp } from "../../services/authService.js";
import { cancelSubscriptionWithOtp } from "../../services/paymentService.js";

export default function Header({ toggle, onSearch, onSearchSubmit, viewMode, onViewToggle }) {

  const navigate = useNavigate();
  const user = getUser();
  const [anchorEl, setAnchorEl] = useState(null);

  // Cancellation dialog state
  const [dialogOpen, setDialogOpen]   = useState(false);
  const [step, setStep]               = useState(1); // 1 = confirm intent, 2 = enter OTP
  const [otp, setOtp]                 = useState("");
  const [sending, setSending]         = useState(false);
  const [cancelling, setCancelling]   = useState(false);
  const [error, setError]             = useState("");
  const [success, setSuccess]         = useState(false);

  const handleOpen  = (e) => setAnchorEl(e.currentTarget);
  const handleClose = ()  => setAnchorEl(null);

  const openCancelDialog = () => {
    setStep(1); setOtp(""); setError(""); setSuccess(false);
    setDialogOpen(true);
  };

  const closeCancelDialog = () => {
    if (cancelling || sending) return;
    setDialogOpen(false);
  };

  // Step 1 → send OTP
  const handleSendOtp = async () => {
    setSending(true); setError("");
    try {
      await sendOtp(user.email);
      setStep(2);
    } catch (err) {
      setError(err.response?.data || "Failed to send OTP. Please try again.");
    } finally {
      setSending(false);
    }
  };

  // Step 2 → verify OTP + cancel
  const handleConfirmCancel = async () => {
    if (!otp.trim()) { setError("Enter the OTP"); return; }
    setCancelling(true); setError("");
    try {
      await cancelSubscriptionWithOtp(user.id, otp.trim());
      // Update localStorage — remove premium
      setUser({ ...user, isPremium: false });
      setSuccess(true);
      setTimeout(() => { setDialogOpen(false); window.location.reload(); }, 2000);
    } catch (err) {
      setError(err.response?.data || "Cancellation failed. Please try again.");
    } finally {
      setCancelling(false);
    }
  };

  return (
    <>
      <AppBar
        position="fixed"
        sx={{ bgcolor: "#fff", color: "#5f6368", boxShadow: "none", borderBottom: "1px solid #dadce0", zIndex: 1201 }}
      >
        <Toolbar sx={{ justifyContent: "space-between" }}>

          {/* LEFT */}
          <Box sx={{ display: "flex", alignItems: "center", width: 240 }}>
            <Tooltip title="Menu">
              <IconButton onClick={toggle}><MenuIcon /></IconButton>
            </Tooltip>
            <Box sx={{ display: "flex", alignItems: "center", ml: 1 }}>
              <img src="https://www.gstatic.com/images/branding/product/1x/keep_2020q4_48dp.png" alt="keep" width="40" height="40" />
              <Typography sx={{ ml: 1, fontSize: 22 }}>Fundoo Notes</Typography>
            </Box>
          </Box>

          {/* CENTER (SEARCH) */}
          <Box sx={{ flexGrow: 1, display: "flex", justifyContent: "flex-start", pl: 6 }}>
            <Box sx={{ display: "flex", alignItems: "center", bgcolor: "#f1f3f4", px: 2, height: 46, borderRadius: 2, width: "100%", maxWidth: 720 }}>
              <SearchIcon sx={{ mr: 1 }} />
              <InputBase
                placeholder="Search"
                sx={{ width: "100%" }}
                onChange={(e) => onSearch && onSearch(e.target.value)}
                onKeyDown={(e) => e.key === "Enter" && onSearchSubmit && onSearchSubmit(e.target.value)}
              />
            </Box>
          </Box>

          {/* RIGHT */}
          <Box sx={{ display: "flex", alignItems: "center", justifyContent: "flex-end", width: 240 }}>
            <Tooltip title="Refresh">
              <IconButton><RefreshIcon /></IconButton>
            </Tooltip>

            <Tooltip title={viewMode === "grid" ? "List view" : "Grid view"}>
              <IconButton onClick={onViewToggle}>
                {viewMode === "grid" ? <ViewAgendaOutlinedIcon /> : <GridViewOutlinedIcon />}
              </IconButton>
            </Tooltip>

            <Tooltip title="Settings">
              <IconButton sx={{ mr: 5 }}><SettingsOutlinedIcon /></IconButton>
            </Tooltip>

            {user?.isPremium ? (
              <Tooltip title="Manage subscription">
                <Chip
                  label="PRO"
                  icon={<WorkspacePremiumOutlinedIcon sx={{ fontSize: 14, color: "#fff !important" }} />}
                  size="small"
                  onClick={openCancelDialog}
                  sx={{ bgcolor: "#1a73e8", color: "#fff", fontWeight: 700, mr: 1, letterSpacing: 1, cursor: "pointer" }}
                />
              </Tooltip>
            ) : (
              <Button
                variant="outlined" size="small"
                onClick={() => navigate("/payment")}
                sx={{ textTransform: "none", borderColor: "#1a73e8", color: "#1a73e8", mr: 1, fontWeight: 600 }}
              >
                Upgrade
              </Button>
            )}

            <Tooltip title="Apps"><IconButton><AppsIcon /></IconButton></Tooltip>
            <Tooltip title="Account">
              <IconButton onClick={handleOpen}><AccountCircle /></IconButton>
            </Tooltip>
            <AccountMenu anchorEl={anchorEl} onClose={handleClose} />
          </Box>
        </Toolbar>
      </AppBar>

      {/* Cancellation Dialog */}
      <Dialog open={dialogOpen} onClose={closeCancelDialog} maxWidth="xs" fullWidth>
        <DialogTitle sx={{ fontWeight: 600 }}>
          {success ? "Subscription Cancelled" : "Manage Subscription"}
        </DialogTitle>

        <DialogContent>
          {success ? (
            <Alert severity="success">
              Your PRO subscription has been cancelled. You've been downgraded to the free plan.
            </Alert>
          ) : (
            <>
              {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

              {step === 1 && (
                <>
                  <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 2 }}>
                    <WorkspacePremiumOutlinedIcon sx={{ color: "#1a73e8" }} />
                    <Typography fontWeight={500}>You are a PRO member</Typography>
                  </Box>
                  <Typography variant="body2" color="text.secondary">
                    Cancelling will remove access to premium backgrounds and other PRO features at the end of your billing period.
                  </Typography>
                  <Typography variant="body2" color="text.secondary" mt={1}>
                    An OTP will be sent to <strong>{user?.email}</strong> to confirm.
                  </Typography>
                </>
              )}

              {step === 2 && (
                <>
                  <Typography variant="body2" color="text.secondary" mb={2}>
                    Enter the 6-digit OTP sent to <strong>{user?.email}</strong>
                  </Typography>
                  <TextField
                    label="Enter OTP" fullWidth autoFocus
                    value={otp} onChange={(e) => setOtp(e.target.value)}
                    inputProps={{ maxLength: 6 }}
                  />
                </>
              )}
            </>
          )}
        </DialogContent>

        {!success && (
          <DialogActions sx={{ px: 3, pb: 2 }}>
            <Button onClick={closeCancelDialog} sx={{ textTransform: "none" }}>
              Keep PRO
            </Button>
            {step === 1 ? (
              <Button
                variant="contained" color="error"
                sx={{ textTransform: "none" }}
                onClick={handleSendOtp}
                disabled={sending}
              >
                {sending ? <CircularProgress size={18} color="inherit" /> : "Cancel Subscription"}
              </Button>
            ) : (
              <Button
                variant="contained" color="error"
                sx={{ textTransform: "none" }}
                onClick={handleConfirmCancel}
                disabled={cancelling}
              >
                {cancelling ? <CircularProgress size={18} color="inherit" /> : "Confirm Cancellation"}
              </Button>
            )}
          </DialogActions>
        )}
      </Dialog>
    </>
  );
}
