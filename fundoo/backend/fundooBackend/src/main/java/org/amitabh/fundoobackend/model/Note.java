package org.amitabh.fundoobackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String bgColor;

    private String bgImage;

    @JsonProperty("isTrash")
    @Column(name = "is_trash", nullable = false)
    private boolean trash = false;

    @JsonProperty("isArchive")
    @Column(name = "is_archive", nullable = false)
    private boolean archive = false;

    @JsonProperty("isPinned")
    @Column(name = "is_pinned", nullable = false)
    private boolean pinned = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "reminder_time")
    private LocalDateTime reminderTime;
}
