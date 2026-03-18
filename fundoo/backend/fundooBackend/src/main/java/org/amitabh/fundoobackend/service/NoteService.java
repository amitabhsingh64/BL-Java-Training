package org.amitabh.fundoobackend.service;

import org.amitabh.fundoobackend.dto.NoteRequest;
import org.amitabh.fundoobackend.dto.NoteResponse;
import org.amitabh.fundoobackend.dto.NoteUpdateRequest;

import java.util.List;

public interface NoteService {

    List<NoteResponse> getAllNotes();

    NoteResponse createNote(NoteRequest request);

    NoteResponse updateNote(String id, NoteUpdateRequest request);

    void deleteNote(String id);
}
