package in.sikkim_allied_health_care_council.server.dto.submissions;

import in.sikkim_allied_health_care_council.server.models.submissions.Grievance;

import java.time.Instant;
import java.util.UUID;

public record GrievanceResponse(
        UUID uuid,
        String name,
        String email,
        String phone,
        String registrationNumber,
        String category,
        String subject,
        String message,
        boolean resolved,
        Instant createdAt
) {

    public static GrievanceResponse from(Grievance g) {
        return new GrievanceResponse(
                g.getUuid(),
                g.getName(),
                g.getEmail(),
                g.getPhone(),
                g.getRegistrationNumber(),
                g.getCategory(),
                g.getSubject(),
                g.getMessage(),
                g.isResolved(),
                g.getCreatedAt()
        );
    }
}
