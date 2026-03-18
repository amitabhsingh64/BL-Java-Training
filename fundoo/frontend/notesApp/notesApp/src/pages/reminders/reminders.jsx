import React, { useState, useEffect } from "react";
import { Box, Grid, CircularProgress, Typography } from "@mui/material";
import Header from "../../components/headerComponent/headerComponent.jsx";
import Sidebar from "../../components/sideBar/sideBar.jsx";
import NoteCard from "../../components/noteCard/noteCard.jsx";
import { getNotes } from "../../services/notesService.js";

const drawerWidth = 240;

const Reminders = () => {
  const [open, setOpen] = useState(false);
  const [notesList, setNotesList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [refreshTrigger, setRefreshTrigger] = useState(false);

  const toggle = () => setOpen((prev) => !prev);

  useEffect(() => {
    const fetchReminderNotes = async () => {
      try {
        const user = JSON.parse(localStorage.getItem("user"));
        const userId = user ? user.id : null;
        const allNotes = await getNotes();
        const reminderNotes = allNotes.filter(
          (note) =>
            note.userId === userId &&
            note.reminderTime != null &&
            note.isTrash !== true
        );
        // Sort by soonest reminder first
        reminderNotes.sort((a, b) => new Date(a.reminderTime) - new Date(b.reminderTime));
        setNotesList(reminderNotes);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching reminder notes:", error);
        setLoading(false);
      }
    };

    fetchReminderNotes();
  }, [refreshTrigger]);

  const autoRefresh = () => setRefreshTrigger((prev) => !prev);

  return (
    <Box sx={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
      <Header toggle={toggle} />
      <Sidebar open={open} onClose={() => setOpen(false)} />

      <Box
        component="main"
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          mt: 2,
          marginLeft: open ? `${drawerWidth}px` : "0px",
          width: open ? `calc(100% - ${drawerWidth}px)` : "100%",
          transition: "margin 0.3s ease, width 0.3s ease",
        }}
      >
        <Typography variant="h6" sx={{ mt: 4, mb: 1, color: "text.secondary", alignSelf: "flex-start", ml: 4 }}>
          Reminders
        </Typography>
        <Box sx={{ width: "100%", maxWidth: "1400px", mt: 1, px: 3 }}>
          {loading ? (
            <Box sx={{ display: "flex", justifyContent: "center", mt: 5 }}>
              <CircularProgress />
            </Box>
          ) : notesList.length === 0 ? (
            <Typography variant="body1" align="center" sx={{ mt: 5, color: "text.secondary" }}>
              Notes with reminders appear here.
            </Typography>
          ) : (
            <Grid container spacing={2}>
              {notesList.map((note) => (
                <Grid
                  item
                  xs={12}
                  sm={6}
                  md={4}
                  lg={3}
                  key={note.id}
                  sx={{ "@media (min-width: 1200px)": { flexBasis: "20%", maxWidth: "20%" } }}
                >
                  <NoteCard note={note} autoRefresh={autoRefresh} />
                </Grid>
              ))}
            </Grid>
          )}
        </Box>
      </Box>
    </Box>
  );
};

export default Reminders;
