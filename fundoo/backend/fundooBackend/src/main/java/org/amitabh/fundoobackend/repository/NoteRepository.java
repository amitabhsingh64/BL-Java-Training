package org.amitabh.fundoobackend.repository;

import org.amitabh.fundoobackend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, String> {

    List<Note> findByUserId(String userId);

    @Query("SELECT n FROM Note n ORDER BY n.pinned DESC, n.createdAt DESC NULLS LAST")
    List<Note> findAllOrderByPinnedAndCreatedAtDesc();

    @Query("SELECT n FROM Note n WHERE n.reminderTime IS NOT NULL AND n.reminderTime <= :now")
    List<Note> findDueReminders(@Param("now") LocalDateTime now);
}
