package in.sikkim_allied_health_care_council.server.dto.auth;

public record LoginResponse(
        String token,
        AdminResponse admin
) {
}
