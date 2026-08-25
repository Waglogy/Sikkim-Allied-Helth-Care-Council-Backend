package in.sikkim_allied_health_care_council.server.dto.members;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateMember(

        @NotBlank(message = "name is required.")
        String name,

        @NotBlank(message = "designation is required")
        String designation,

        String photo,

        String description,

        int priority,

        /** UUID of the category this member falls under (optional). */
        UUID categoryUuid
) {
}
