package in.sikkim_allied_health_care_council.server.dto.submissions;

/** Body for marking a submission resolved / unresolved from the admin panel. */
public record UpdateResolved(
        boolean resolved
) {
}
