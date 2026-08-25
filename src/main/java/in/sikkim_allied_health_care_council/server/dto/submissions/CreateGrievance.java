package in.sikkim_allied_health_care_council.server.dto.submissions;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateGrievance(

        @NotBlank(message = "name is required.")
        String name,

        @NotBlank(message = "email is required.")
        @Email(message = "email must be a valid email address.")
        String email,

        String phone,

        String registrationNumber,

        @NotBlank(message = "category is required.")
        String category,

        @NotBlank(message = "subject is required.")
        String subject,

        @NotBlank(message = "message is required.")
        String message
) {
}
