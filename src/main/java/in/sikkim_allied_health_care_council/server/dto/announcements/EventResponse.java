package in.sikkim_allied_health_care_council.server.dto.announcements;

import in.sikkim_allied_health_care_council.server.models.announcements.Events;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventResponse(
        UUID uuid,
        String name,
        String description,
        LocalDateTime eventDate,
        List<String> images,
        List<String> documents,
        UUID createdBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static EventResponse from(Events event) {
        return new EventResponse(
                event.getUuid(),
                event.getName(),
                event.getDescription(),
                event.getEventDate(),
                event.getImages(),
                event.getDocuments(),
                event.getCreatedBy().getUuid(),
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}
