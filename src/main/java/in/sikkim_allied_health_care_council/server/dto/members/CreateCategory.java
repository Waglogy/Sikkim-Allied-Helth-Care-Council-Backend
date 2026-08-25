package in.sikkim_allied_health_care_council.server.dto.members;

import in.sikkim_allied_health_care_council.server.models.members.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCategory(

        @NotBlank(message = "name is required.")
        String name,

        int priority,

        @NotNull(message = "type is required.")
        CategoryType type
) {
}
