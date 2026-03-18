package org.amitabh.fundoobackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteRequest {
    private String userId;
    private String title;
    private String text;
    private String bgColor;
    private String bgImage;

    @JsonProperty("isTrash")
    private Boolean trash;

    @JsonProperty("isArchive")
    private Boolean archive;

    @JsonProperty("isPinned")
    private Boolean pinned;

    private LocalDateTime reminderTime;
}
