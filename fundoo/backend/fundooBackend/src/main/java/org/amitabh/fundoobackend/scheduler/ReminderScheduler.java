package org.amitabh.fundoobackend.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amitabh.fundoobackend.messaging.EmailProducer;
import org.amitabh.fundoobackend.model.Note;
import org.amitabh.fundoobackend.model.User;
import org.amitabh.fundoobackend.repository.NoteRepository;
import org.amitabh.fundoobackend.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final EmailProducer emailProducer;

    @Scheduled(fixedRate = 30000) // runs every 30 seconds
    public void sendDueReminderEmails() {
        List<Note> dueNotes = noteRepository.findDueReminders(LocalDateTime.now());

        for (Note note : dueNotes) {
            Optional<User> userOpt = userRepository.findById(note.getUserId());
            if (userOpt.isEmpty()) {
                note.setReminderTime(null);
                noteRepository.save(note);
                continue;
            }

            User user = userOpt.get();
            String title = note.getTitle() != null && !note.getTitle().isBlank()
                    ? note.getTitle() : "(No title)";
            String body = note.getText() != null && !note.getText().isBlank()
                    ? note.getText() : "(No content)";

            String emailBody = String.format(
                    "Hi %s,\n\nThis is your reminder for the note:\n\nTitle: %s\n\n%s\n\n— Fundoo Notes",
                    user.getFirstName(), title, body
            );

            try {
                emailProducer.sendEmail(user.getEmail(), "Fundoo Notes Reminder: " + title, emailBody);
                log.info("Sent reminder email for note {} to {}", note.getId(), user.getEmail());
            } catch (Exception e) {
                log.error("Failed to send reminder email for note {}: {}", note.getId(), e.getMessage());
            }

            // Clear reminder after sending so it doesn't fire again
            note.setReminderTime(null);
            noteRepository.save(note);
        }
    }
}
