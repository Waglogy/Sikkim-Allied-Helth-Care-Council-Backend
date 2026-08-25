package in.sikkim_allied_health_care_council.server.dto.announcements;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CreatePublication(

        @NotBlank(message = "title is required.")
        String title,

        String description,

        LocalDateTime publishDate,

        String fileUpload,

        boolean publishStatus
) {
}
