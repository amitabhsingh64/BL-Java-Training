package org.amitabh.fundoobackend.controller;

import lombok.RequiredArgsConstructor;
import org.amitabh.fundoobackend.dto.NoteRequest;
import org.amitabh.fundoobackend.dto.NoteResponse;
import org.amitabh.fundoobackend.dto.NoteUpdateRequest;
import org.amitabh.fundoobackend.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "https://your-app.vercel.app"})
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody NoteRequest request) {
        try {
            NoteResponse response = noteService.createNote(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable String id, @RequestBody NoteUpdateRequest request) {
        try {
            NoteResponse response = noteService.updateNote(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable String id) {
        try {
            noteService.deleteNote(id);
            return ResponseEntity.ok("Note deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
