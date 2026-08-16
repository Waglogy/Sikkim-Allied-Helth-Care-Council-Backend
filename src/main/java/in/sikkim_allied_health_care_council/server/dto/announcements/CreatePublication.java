package in.sikkim_allied_health_care_council.server.dto.announcements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatePublication(

        @NotBlank(message = "title is required.")
        String title,

        String description,

        LocalDateTime publishDate,

        String fileUpload,

        boolean publishStatus,

        @NotNull(message = "createdBy is required.")
        UUID createdBy
) {
}
