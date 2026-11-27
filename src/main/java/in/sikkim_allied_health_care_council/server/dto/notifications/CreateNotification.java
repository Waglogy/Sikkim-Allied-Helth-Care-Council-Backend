package in.sikkim_allied_health_care_council.server.dto.notifications;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateNotification(

        @NotBlank(message = "title is required.")
        String title,

        String document,

        /** Publication date shown on the notice; defaults to today when omitted. */
        LocalDate date
) {
}
