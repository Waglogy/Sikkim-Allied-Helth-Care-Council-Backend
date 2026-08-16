package in.sikkim_allied_health_care_council.server.dto.members;

import jakarta.validation.constraints.NotBlank;

public record CreateMember(

        @NotBlank(message = "name is required.")
        String name,

        @NotBlank(message = "designation is required")
        String designation
) {
}
