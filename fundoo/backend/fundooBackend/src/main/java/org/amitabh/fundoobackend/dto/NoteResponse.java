package org.amitabh.fundoobackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoteResponse {
    private String id;
    private String userId;
    private String title;
    private String text;
    private String bgColor;
    private String bgImage;

    @JsonProperty("isTrash")
    private boolean trash;

    @JsonProperty("isArchive")
    private boolean archive;

    @JsonProperty("isPinned")
    private boolean pinned;

    private LocalDateTime createdAt;

    private LocalDateTime reminderTime;
}
