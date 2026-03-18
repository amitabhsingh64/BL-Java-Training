package org.amitabh.fundoobackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.amitabh.fundoobackend.dto.NoteRequest;
import org.amitabh.fundoobackend.dto.NoteResponse;
import org.amitabh.fundoobackend.dto.NoteUpdateRequest;
import org.amitabh.fundoobackend.model.Note;
import org.amitabh.fundoobackend.repository.NoteRepository;
import org.amitabh.fundoobackend.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAllOrderByPinnedAndCreatedAtDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public NoteResponse createNote(NoteRequest request) {
        Note note = new Note();
        note.setUserId(request.getUserId());
        note.setTitle(request.getTitle());
        note.setText(request.getText());
        note.setBgColor(request.getBgColor());
        note.setBgImage(request.getBgImage());
        note.setTrash(Boolean.TRUE.equals(request.getTrash()));
        note.setArchive(Boolean.TRUE.equals(request.getArchive()));
        note.setPinned(Boolean.TRUE.equals(request.getPinned()));
        note.setReminderTime(request.getReminderTime());

        return toResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse updateNote(String id, NoteUpdateRequest request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));

        if (request.getTitle() != null)    note.setTitle(request.getTitle());
        if (request.getText() != null)     note.setText(request.getText());
        if (request.getBgColor() != null)  note.setBgColor(request.getBgColor());
        if (request.getBgImage() != null)  note.setBgImage(request.getBgImage());
        if (request.getTrash() != null)    note.setTrash(request.getTrash());
        if (request.getArchive() != null)  note.setArchive(request.getArchive());
        if (request.getPinned() != null)   note.setPinned(request.getPinned());
        if (Boolean.TRUE.equals(request.getClearReminder())) note.setReminderTime(null);
        else if (request.getReminderTime() != null)          note.setReminderTime(request.getReminderTime());

        return toResponse(noteRepository.save(note));
    }

    @Override
    public void deleteNote(String id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getUserId(),
                note.getTitle(),
                note.getText(),
                note.getBgColor(),
                note.getBgImage(),
                note.isTrash(),
                note.isArchive(),
                note.isPinned(),
                note.getCreatedAt(),
                note.getReminderTime()
        );
    }
}
