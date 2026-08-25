package in.sikkim_allied_health_care_council.server.dto.announcements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEvent(

        @NotBlank(message = "name is required.")
        String name,

        String description,

        @NotNull(message = "eventDate is required.")
        LocalDateTime eventDate,

        List<String> images,

        List<String> documents
) {
}
