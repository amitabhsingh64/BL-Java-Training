import React, { useState, useEffect } from "react";
import { Box, Grid, CircularProgress, Typography } from "@mui/material";
import Header from "../../components/headerComponent/headerComponent.jsx";
import Sidebar from "../../components/sideBar/sideBar.jsx";
import { Outlet } from "react-router-dom";
import AddNotesBlock from "../../components/addNotesBlock/addNotesBlock.jsx";
import NoteCard from "../../components/noteCard/noteCard.jsx";
import { getNotes } from "../../services/notesService.js";

const drawerWidth = 240; 

const Dashboard = () => {
  const [open, setOpen] = useState(false);
  const [notesList, setNotesList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [refreshTrigger, setRefreshTrigger] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [submittedQuery, setSubmittedQuery] = useState("");
  const [viewMode, setViewMode] = useState("grid"); // "grid" | "list"

  const toggle = () => {
    setOpen((prev) => !prev);
  };

  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const user = JSON.parse(localStorage.getItem("user"));
        const userId = user ? user.id : null;
        const allNotes = await getNotes();
        const myNotes = allNotes.filter(note =>
          note.userId === userId &&
          note.isTrash !== true &&
          note.isArchive !== true &&
          note.reminderTime == null
        );
        setNotesList(myNotes);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching notes:", error);
        setLoading(false);
      }
    };

    fetchNotes();
  }, [refreshTrigger]);

  const autoRefresh = () => {
    setRefreshTrigger(prev => !prev);
  };

  const getDisplayedNotes = () => {
    const q = searchQuery.trim().toLowerCase();
    if (!q) return notesList;

    const matches = notesList.filter(n => (n.title || "").toLowerCase().includes(q));
    const rest    = notesList.filter(n => !(n.title || "").toLowerCase().includes(q));
    return [...matches, ...rest];
  };

  const getExactMatchId = () => {
    const q = submittedQuery.trim().toLowerCase();
    if (!q) return null;
    const exact = notesList.find(n => (n.title || "").toLowerCase() === q);
    return exact ? exact.id : null;
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Header
        toggle={toggle}
        onSearch={(q) => { setSearchQuery(q); if (!q.trim()) setSubmittedQuery(""); }}
        onSearchSubmit={setSubmittedQuery}
        viewMode={viewMode}
        onViewToggle={() => setViewMode(v => v === "grid" ? "list" : "grid")}
      />
      <Sidebar open={open} onClose={() => setOpen(false)} />

      <Box 
        component="main" 
        sx={{ 
          display: 'flex', flexDirection: 'column', alignItems: 'center', mt: 2,
          marginLeft: open ? `${drawerWidth}px` : '0px', 
          width: open ? `calc(100% - ${drawerWidth}px)` : '100%', 
          transition: 'margin 0.3s ease, width 0.3s ease',
        }}
      >
        <AddNotesBlock autoRefresh={autoRefresh} />

        <Box sx={{ width: '100%', maxWidth: '1400px', mt: 5, px: 3 }}>
          {loading ? (
             <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}><CircularProgress /></Box>
          ) : notesList.length === 0 ? (
             <Typography variant="body1" align="center" sx={{ mt: 5, color: 'text.secondary' }}>
                Your notes appear here
             </Typography>
          ) : viewMode === "list" ? (
            <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", gap: 2 }}>
              {getDisplayedNotes().map((note) => (
                <Box key={note.id} sx={{ width: "100%", maxWidth: 600 }}>
                  <NoteCard note={note} autoRefresh={autoRefresh} forceOpen={note.id === getExactMatchId()} listView />
                </Box>
              ))}
            </Box>
          ) : (
            <Grid container spacing={2}>
              {getDisplayedNotes().map((note) => (
                <Grid
                  item xs={12} sm={6} md={4} lg={3} key={note.id}
                  sx={{ '@media (min-width: 1200px)': { flexBasis: '20%', maxWidth: '20%' } }}
                >
                  <NoteCard note={note} autoRefresh={autoRefresh} forceOpen={note.id === getExactMatchId()} />
                </Grid>
              ))}
            </Grid>
          )}
        </Box>
        <Outlet />
      </Box>
    </Box>
  );
};

export default Dashboard;