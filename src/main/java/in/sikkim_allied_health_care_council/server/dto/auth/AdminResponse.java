package in.sikkim_allied_health_care_council.server.dto.auth;

import in.sikkim_allied_health_care_council.server.models.admin.AdminRole;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;

import java.util.UUID;

/**
 * Client-facing view of an {@link AdminUser}. Deliberately omits the password
 * hash and the internal database id.
 */
public record AdminResponse(
        UUID uuid,
        String name,
        String email,
        AdminRole role
) {

    public static AdminResponse from(AdminUser admin) {
        return new AdminResponse(
                admin.getUuid(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole()
        );
    }
}
