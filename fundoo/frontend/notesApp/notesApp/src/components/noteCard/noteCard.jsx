import React, { useState, useRef, useEffect } from "react";
import { updateNote, deleteNote } from "../../services/notesService.js";
import { getUser } from "../../services/authService.js";
import {
  Card,
  CardContent,
  CardActions,
  Typography,
  IconButton,
  Box,
  Tooltip,
  Dialog,
  Paper,
  TextareaAutosize,
  Menu,
  MenuItem,
  ListItemText,
  Chip,
} from "@mui/material";
import PushPinOutlinedIcon from "@mui/icons-material/PushPinOutlined";
import PushPinIcon from "@mui/icons-material/PushPin";
import AddAlertOutlinedIcon from "@mui/icons-material/AddAlertOutlined";
import PersonAddAlt1OutlinedIcon from "@mui/icons-material/PersonAddAlt1Outlined";
import ColorLensOutlinedIcon from "@mui/icons-material/ColorLensOutlined";
import ImageOutlinedIcon from "@mui/icons-material/ImageOutlined";
import ArchiveOutlinedIcon from "@mui/icons-material/ArchiveOutlined";
import MoreVertOutlinedIcon from "@mui/icons-material/MoreVertOutlined";
import DeleteOutlinedIcon from "@mui/icons-material/DeleteOutlined";
import RestoreFromTrashOutlinedIcon from "@mui/icons-material/RestoreFromTrashOutlined";
import DeleteForeverOutlinedIcon from "@mui/icons-material/DeleteForeverOutlined";
import ColorPallet from "../colorPallet/colorPallet.jsx";
import groceryImg from "../../assets/colorPalletBgImg/grocery_light_thumb_0615.svg";
import foodImg from "../../assets/colorPalletBgImg/food_light_thumb_0615.svg";
import travelImg from "../../assets/colorPalletBgImg/travel_light_thumb_0615.svg";
import placesImg from "../../assets/colorPalletBgImg/places_light_thumb_0615.svg";
import videoImg from "../../assets/colorPalletBgImg/video_light_thumb_0615.svg";
import celebrationImg from "../../assets/colorPalletBgImg/celebration_light_thumb_0715.svg";
import recipeImg from "../../assets/colorPalletBgImg/recipe_light_thumb_0615.svg";
import default_bg_color from "../../assets/bg_color_default.png";
import dropper from "../../assets/dropper.png";

const colors = [
  { img: dropper, label: "no color" },
  { color: "#faafa8" }, { color: "#f39f76" }, { color: "#fff8b8" },
  { color: "#e2f6d3" }, { color: "#b4ddd3" }, { color: "#d4e4ed" },
  { color: "#aeccdc" }, { color: "#d3bfdb" }, { color: "#f6e2dd" },
  { color: "#e9e3d4" }, { color: "#efeff1" },
];

const backgrounds = [
  { img: default_bg_color, label: "No background" },
  { img: groceryImg, label: "Grocery" }, { img: foodImg, label: "Food" },
  { img: travelImg, label: "Travel" }, { img: placesImg, label: "Places" },
  { img: videoImg, label: "Video" }, { img: celebrationImg, label: "Celebration" },
  { img: recipeImg, label: "Recipe" },
];

// Color and image are independent — selecting one never clears the other.
// bgColor → expressed as the card border.
// bgImage → expressed as the card background image.
const buildColorUpdate = (type, index) => {
  if (type === "color") {
    return { bgColor: index === 0 ? "#ffffff" : colors[index].color };
  }
  return { bgImage: index === 0 ? "" : backgrounds[index].img };
};

const hasColor = (bgColor) => bgColor && bgColor !== "#ffffff";

// Border is colored only when BOTH color and image are applied together.
// When only color is set, it fills the background instead.
const cardBorder  = (bgColor, bgImage) =>
  hasColor(bgColor) && bgImage ? `3px solid ${bgColor}` : "1px solid #e0e0e0";

// Background fill: use color when no image, otherwise white so image shows cleanly.
const cardBgColor = (bgColor, bgImage) =>
  !bgImage && hasColor(bgColor) ? bgColor : "#ffffff";

function NoteCard({ note, autoRefresh, forceOpen, listView }) {
  const isPremium = getUser()?.isPremium ?? false;
  // Card hover / palette state
  const [isHovered, setIsHovered]       = useState(false);
  const [paletteAnchorEl, setPaletteAnchorEl] = useState(null);
  const isPaletteOpen = Boolean(paletteAnchorEl);

  // Reminder menu state
  const [reminderAnchorEl, setReminderAnchorEl] = useState(null);
  const isReminderMenuOpen = Boolean(reminderAnchorEl);

  // Dialog state
  const [isEditOpen, setIsEditOpen]     = useState(false);
  const [editTitle, setEditTitle]       = useState(note.title   || "");
  const [editText,  setEditText]        = useState(note.text    || "");
  const [editBgColor, setEditBgColor]   = useState(note.bgColor || "#ffffff");
  const [editBgImage, setEditBgImage]   = useState(note.bgImage || "");
  const dialogPaletteRef               = useRef(null);
  const [dialogPaletteAnchorEl, setDialogPaletteAnchorEl] = useState(null);
  const isDialogPaletteOpen = Boolean(dialogPaletteAnchorEl);

  // Open dialog when exact search match
  useEffect(() => {
    if (forceOpen && !note.isTrash) {
      setEditTitle(note.title   || "");
      setEditText(note.text     || "");
      setEditBgColor(note.bgColor || "#ffffff");
      setEditBgImage(note.bgImage || "");
      setIsEditOpen(true);
    } else if (!forceOpen) {
      setIsEditOpen(false);
    }
  }, [forceOpen]);

  //Open dialog
  const handleCardDoubleClick = () => {
    if (note.isTrash) return;
    setEditTitle(note.title   || "");
    setEditText(note.text     || "");
    setEditBgColor(note.bgColor || "#ffffff");
    setEditBgImage(note.bgImage || "");
    setIsEditOpen(true);
  };

  // Close dialog: save title/text if changed 
  const handleEditClose = async () => {
    setIsEditOpen(false);
    setDialogPaletteAnchorEl(null);
    const titleChanged = editTitle.trim() !== (note.title || "");
    const textChanged  = editText.trim()  !== (note.text  || "");
    if (!titleChanged && !textChanged) return;
    try {
      await updateNote(note.id, { title: editTitle, text: editText });
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error saving note:", error);
    }
  };

  // Card palette
  const handlePaletteClick = (e) => {
    e.stopPropagation();
    setPaletteAnchorEl(e.currentTarget);
  };

  // Shared: persist color/bg update, refresh list
  const applyColorUpdate = async (updateData) => {
    try {
      await updateNote(note.id, updateData);
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error updating style:", error);
    }
  };

  // Card palette handler
  const handleColorUpdate = (type, index) =>
    applyColorUpdate(buildColorUpdate(type, index));

  // Dialog palette handler — updates only the changed field for live preview
  const handleDialogColorUpdate = (type, index) => {
    const updateData = buildColorUpdate(type, index);
    if (type === "color") setEditBgColor(updateData.bgColor);
    else                  setEditBgImage(updateData.bgImage);
    setDialogPaletteAnchorEl(null);
    applyColorUpdate(updateData);
  };

  // Trash (card + dialog)
  const trashNote = async (e) => {
    e.stopPropagation();
    try {
      if (note.isTrash) {
        await deleteNote(note.id);
      } else {
        await updateNote(note.id, { isTrash: true, isArchive: false });
      }
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error trashing note:", error);
    }
  };

  const handleDialogTrash = async (e) => {
    setIsEditOpen(false);
    await trashNote(e);
  };

  // Archive (card + dialog)
  const archiveNote = async (e) => {
    e.stopPropagation();
    try {
      await updateNote(note.id, { isArchive: !note.isArchive, isTrash: false });
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error archiving note:", error);
    }
  };

  const handleDialogArchive = async (e) => {
    setIsEditOpen(false);
    await archiveNote(e);
  };

  // Pin / Unpin
  const togglePin = async (e) => {
    e.stopPropagation();
    try {
      await updateNote(note.id, { isPinned: !note.isPinned });
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error toggling pin:", error);
    }
  };

  // Restore
  const restoreNote = async (e) => {
    e.stopPropagation();
    try {
      await updateNote(note.id, { isTrash: false, isArchive: false });
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error restoring note:", error);
    }
  };

  // Reminder
  const REMINDER_OPTIONS = [
    { label: "30 seconds", ms: 30 * 1000 },
    { label: "5 minutes",  ms: 5 * 60 * 1000 },
    { label: "1 hour",     ms: 60 * 60 * 1000 },
    { label: "1 day",      ms: 24 * 60 * 60 * 1000 },
  ];

  const toLocalISOString = (date) => {
    const pad = (n) => String(n).padStart(2, "0");
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
  };

  const setReminder = async (ms) => {
    setReminderAnchorEl(null);
    const reminderTime = toLocalISOString(new Date(Date.now() + ms));
    try {
      await updateNote(note.id, { reminderTime });
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error setting reminder:", error);
    }
  };

  const clearReminder = async () => {
    setReminderAnchorEl(null);
    try {
      await updateNote(note.id, { clearReminder: true });
      if (autoRefresh) autoRefresh();
    } catch (error) {
      console.error("Error clearing reminder:", error);
    }
  };

  const formatReminderLabel = (reminderTime) => {
    if (!reminderTime) return null;
    const d = new Date(reminderTime);
    return d.toLocaleString([], { month: "short", day: "numeric", hour: "2-digit", minute: "2-digit" });
  };

  // Shared toolbar JSX (reused in card and dialog)
  const renderToolbar = ({ onPaletteClick, onArchive, onTrash, onClose }) => (
    <Box
      onClick={(e) => e.stopPropagation()}
      sx={{ display: "flex", alignItems: "center", px: 1, pb: 0.5 }}
    >
      <Tooltip title="Remind me">
        <IconButton size="small" onClick={(e) => { e.stopPropagation(); setReminderAnchorEl(e.currentTarget); }}>
          <AddAlertOutlinedIcon fontSize="inherit" color={note.reminderTime ? "primary" : "inherit"} />
        </IconButton>
      </Tooltip>
      <Tooltip title="Collaborator">
        <IconButton size="small"><PersonAddAlt1OutlinedIcon fontSize="inherit" /></IconButton>
      </Tooltip>
      <Tooltip title="Background options">
        <IconButton size="small" onClick={onPaletteClick}>
          <ColorLensOutlinedIcon fontSize="inherit" />
        </IconButton>
      </Tooltip>
      <Tooltip title="Add image">
        <IconButton size="small"><ImageOutlinedIcon fontSize="inherit" /></IconButton>
      </Tooltip>
      <Tooltip title="Archive">
        <IconButton size="small" onClick={onArchive}>
          <ArchiveOutlinedIcon fontSize="inherit" />
        </IconButton>
      </Tooltip>
      <Tooltip title="Delete">
        <IconButton size="small" onClick={onTrash}>
          <DeleteOutlinedIcon fontSize="inherit" />
        </IconButton>
      </Tooltip>
      <Tooltip title="More">
        <IconButton size="small"><MoreVertOutlinedIcon fontSize="inherit" /></IconButton>
      </Tooltip>
      {onClose && (
        <Typography
          onClick={onClose}
          sx={{
            ml: "auto", mr: 1, fontSize: "0.9rem", fontWeight: 500,
            cursor: "pointer", px: 2, py: 0.75, borderRadius: 1,
            "&:hover": { backgroundColor: "rgba(0,0,0,0.06)" },
          }}
        >
          Close
        </Typography>
      )}
    </Box>
  );

  const cardSharedSx = {
    width: "100%",
    borderRadius: 2,
    backgroundColor: cardBgColor(note.bgColor, note.bgImage),
    border: cardBorder(note.bgColor, note.bgImage),
    position: "relative",
    cursor: "default",
    transition: "box-shadow 0.3s, border 0.2s",
    "&:hover": { boxShadow: "0 1px 2px 0 rgba(60,64,67,0.3), 0 1px 3px 1px rgba(60,64,67,0.15)" },
    overflow: "visible",
  };

  return (
    <Card
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      sx={listView
        ? { ...cardSharedSx, maxWidth: "100%", minHeight: 100 }
        : { ...cardSharedSx, maxWidth: "300px", ml: 10, mt: 5 }
      }
      elevation={0}
    >
      {/* Card bg image */}
      {note.bgImage && (
        <Box sx={{
          position: "absolute", top: 0, right: 0, bottom: 0, left: 0,
          backgroundImage: `url(${note.bgImage})`, backgroundSize: "cover",
          backgroundPosition: "center bottom", opacity: 0.9, zIndex: 0,
          pointerEvents: "none", borderRadius: 1,
        }} />
      )}

      {listView ? (
        /* ── List view: same layout as grid, full column width ── */
        <Box sx={{ position: "relative", zIndex: 1 }}>
          <Tooltip title={note.isPinned ? "Unpin note" : "Pin note"}>
            <IconButton size="small" onClick={togglePin} sx={{
              position: "absolute", top: 5, right: 5,
              opacity: isHovered || note.isPinned ? 1 : 0, transition: "opacity 0.2s",
            }}>
              {note.isPinned ? <PushPinIcon fontSize="small" /> : <PushPinOutlinedIcon fontSize="small" />}
            </IconButton>
          </Tooltip>

          <CardContent onDoubleClick={handleCardDoubleClick} sx={{ pb: 0, minHeight: 80, cursor: "text" }}>
            <Typography variant="h6" component="div" sx={{ fontWeight: 500, fontSize: "1rem", mb: 1, wordWrap: "break-word", pr: 3 }}>
              {note.title}
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ wordWrap: "break-word", mb: 2 }}>
              {note.text}
            </Typography>
          </CardContent>

          <CardActions
            onClick={(e) => e.stopPropagation()}
            sx={{ justifyContent: "space-between", px: 1, opacity: isHovered || isPaletteOpen ? 1 : 0, transition: "opacity 0.2s" }}
          >
            {note.isTrash ? (
              <>
                <Tooltip title="Restore"><IconButton size="small" onClick={restoreNote}><RestoreFromTrashOutlinedIcon fontSize="inherit" /></IconButton></Tooltip>
                <Tooltip title="Delete Forever"><IconButton size="small" onClick={trashNote}><DeleteForeverOutlinedIcon fontSize="inherit" /></IconButton></Tooltip>
              </>
            ) : renderToolbar({ onPaletteClick: handlePaletteClick, onArchive: archiveNote, onTrash: trashNote, onClose: null })}
          </CardActions>
        </Box>
      ) : (
        /* ── Grid view: original card layout ── */
        <Box sx={{ position: "relative", zIndex: 1 }}>
          {/* Pin */}
          <Tooltip title={note.isPinned ? "Unpin note" : "Pin note"}>
            <IconButton size="small" onClick={togglePin} sx={{
              position: "absolute", top: 5, right: 5,
              opacity: isHovered || note.isPinned ? 1 : 0, transition: "opacity 0.2s",
            }}>
              {note.isPinned ? <PushPinIcon fontSize="small" /> : <PushPinOutlinedIcon fontSize="small" />}
            </IconButton>
          </Tooltip>

          {/* Title + text — double-click opens dialog */}
          <CardContent
            onDoubleClick={handleCardDoubleClick}
            sx={{ pb: 0, minHeight: "100px", cursor: "text" }}
          >
            <Typography variant="h6" component="div" sx={{ fontWeight: 500, fontSize: "1rem", mb: 1, wordWrap: "break-word", pr: 3 }}>
              {note.title}
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ wordWrap: "break-word", mb: 2 }}>
              {note.text}
            </Typography>
          </CardContent>

          {/* Card action toolbar */}
          <CardActions
            onClick={(e) => e.stopPropagation()}
            sx={{
              justifyContent: "space-between", px: 1,
              opacity: isHovered || isPaletteOpen ? 1 : 0,
              transition: "opacity 0.2s",
            }}
          >
            {note.isTrash ? (
              <>
                <Tooltip title="Restore">
                  <IconButton size="small" onClick={restoreNote}>
                    <RestoreFromTrashOutlinedIcon fontSize="inherit" />
                  </IconButton>
                </Tooltip>
                <Tooltip title="Delete Forever">
                  <IconButton size="small" onClick={trashNote}>
                    <DeleteForeverOutlinedIcon fontSize="inherit" />
                  </IconButton>
                </Tooltip>
              </>
            ) : renderToolbar({
              onPaletteClick: handlePaletteClick,
              onArchive: archiveNote,
              onTrash: trashNote,
              onClose: null,
            })}
          </CardActions>
        </Box>
      )}

      {/* Reminder chip */}
      {note.reminderTime && (
        <Box sx={{ px: 2, pb: 1, position: "relative", zIndex: 1 }}>
          <Chip
            icon={<AddAlertOutlinedIcon />}
            label={formatReminderLabel(note.reminderTime)}
            size="small"
            color="primary"
            variant="outlined"
            onDelete={clearReminder}
            sx={{ fontSize: "0.72rem" }}
          />
        </Box>
      )}

      {/* Reminder menu */}
      <Menu
        anchorEl={reminderAnchorEl}
        open={isReminderMenuOpen}
        onClose={() => setReminderAnchorEl(null)}
        onClick={(e) => e.stopPropagation()}
      >
        {REMINDER_OPTIONS.map((opt) => (
          <MenuItem key={opt.label} onClick={() => setReminder(opt.ms)}>
            <ListItemText primary={opt.label} />
          </MenuItem>
        ))}
        {note.reminderTime && (
          <MenuItem onClick={clearReminder}>
            <ListItemText primary="Remove reminder" sx={{ color: "error.main" }} />
          </MenuItem>
        )}
      </Menu>

      {/* Card color palette */}
      <ColorPallet
        openState={isPaletteOpen}
        anchorEl={paletteAnchorEl}
        onClose={() => setPaletteAnchorEl(null)}
        onColorSelect={(index) => handleColorUpdate("color", index)}
        onBackgroundSelect={(index) => handleColorUpdate("image", index)}
        isPremium={isPremium}
      />

      {/* ── Dialog: exact replica of the card ── */}
      <Dialog
        open={isEditOpen}
        onClose={handleEditClose}
        onClick={(e) => e.stopPropagation()}
        PaperProps={{
          sx: {
            width: 600, borderRadius: 2, overflow: "visible",
            backgroundColor: cardBgColor(editBgColor, editBgImage),
            border: cardBorder(editBgColor, editBgImage),
            boxShadow: "0 8px 24px rgba(0,0,0,0.2)",
          },
        }}
      >
        <Paper elevation={0} sx={{ position: "relative", backgroundColor: "transparent", borderRadius: 2, overflow: "hidden" }}>

          {/* Dialog bg image overlay */}
          {editBgImage && (
            <Box sx={{
              position: "absolute", top: 0, right: 0, bottom: 0, left: 0,
              backgroundImage: `url(${editBgImage})`, backgroundSize: "cover",
              backgroundPosition: "center bottom", opacity: 0.9, zIndex: 0,
              pointerEvents: "none",
            }} />
          )}

          <Box sx={{ position: "relative", zIndex: 1, p: 2}}>
            {/* Pin */}
            <Tooltip title={note.isPinned ? "Unpin note" : "Pin note"}>
              <IconButton size="small" onClick={togglePin} sx={{ position: "absolute", top: 8, right: 8 }}>
                {note.isPinned ? <PushPinIcon fontSize="small" /> : <PushPinOutlinedIcon fontSize="small" />}
              </IconButton>
            </Tooltip>

            {/* Title */}
            <TextareaAutosize
              placeholder="Title"
              value={editTitle}
              onChange={(e) => setEditTitle(e.target.value)}
              style={{
                width: "calc(100% - 40px)", border: "none", outline: "none",
                fontSize: "1.1rem", fontWeight: 600, resize: "none",
                backgroundColor: "transparent", fontFamily: "inherit",
              }}
            />

            {/* Text */}
            <TextareaAutosize
              placeholder="Take a note..."
              value={editText}
              onChange={(e) => setEditText(e.target.value)}
              style={{
                width: "100%", border: "none", outline: "none",
                fontSize: "0.95rem", resize: "none",
                backgroundColor: "transparent", fontFamily: "inherit",
                marginTop: "8px", minHeight: "80px", display: "block",
              }}
            />

            {/* Toolbar — identical icons, wired to dialog handlers */}
            <Box ref={dialogPaletteRef}>
              {renderToolbar({
                onPaletteClick: (e) => {
                  e.stopPropagation();
                  setDialogPaletteAnchorEl(e.currentTarget);
                },
                onArchive: handleDialogArchive,
                onTrash:   handleDialogTrash,
                onClose:   handleEditClose,
              })}
            </Box>

            {/* Dialog color palette */}
            <ColorPallet
              openState={isDialogPaletteOpen}
              anchorEl={dialogPaletteAnchorEl}
              onClose={() => setDialogPaletteAnchorEl(null)}
              onColorSelect={(index) => handleDialogColorUpdate("color", index)}
              onBackgroundSelect={(index) => handleDialogColorUpdate("image", index)}
              isPremium={isPremium}
            />
          </Box>
        </Paper>
      </Dialog>
    </Card>
  );
}

export default NoteCard;
