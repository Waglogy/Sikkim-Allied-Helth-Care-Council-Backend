package in.sikkim_allied_health_care_council.server.dto.auth;

import in.sikkim_allied_health_care_council.server.models.admin.AdminRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAdmin(

        @NotBlank(message = "name is required.")
        String name,

        @NotBlank(message = "email is required.")
        @Email(message = "email must be a valid email address.")
        String email,

        @NotBlank(message = "password is required.")
        @Size(min = 8, message = "password must be at least 8 characters long.")
        String password,

        @NotNull(message = "role is required.")
        AdminRole role
) {
}
