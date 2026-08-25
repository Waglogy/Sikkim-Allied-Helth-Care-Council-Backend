package in.sikkim_allied_health_care_council.server.dto.submissions;

import in.sikkim_allied_health_care_council.server.models.submissions.ContactSubmission;

import java.time.Instant;
import java.util.UUID;

public record ContactResponse(
        UUID uuid,
        String name,
        String email,
        String subject,
        String message,
        boolean resolved,
        Instant createdAt
) {

    public static ContactResponse from(ContactSubmission c) {
        return new ContactResponse(
                c.getUuid(),
                c.getName(),
                c.getEmail(),
                c.getSubject(),
                c.getMessage(),
                c.isResolved(),
                c.getCreatedAt()
        );
    }
}
