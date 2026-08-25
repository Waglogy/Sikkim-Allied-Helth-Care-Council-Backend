package in.sikkim_allied_health_care_council.server.dto.notifications;

import jakarta.validation.constraints.NotBlank;

public record CreateNotification(

        @NotBlank(message = "title is required.")
        String title,

        String document
) {
}
