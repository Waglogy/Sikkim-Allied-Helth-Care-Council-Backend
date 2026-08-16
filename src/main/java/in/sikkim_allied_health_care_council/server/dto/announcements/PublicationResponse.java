package in.sikkim_allied_health_care_council.server.dto.announcements;

import in.sikkim_allied_health_care_council.server.models.announcements.Publications;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record PublicationResponse(
        UUID uuid,
        String title,
        String description,
        LocalDateTime publishDate,
        String fileUpload,
        boolean publishStatus,
        UUID createdBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static PublicationResponse from(Publications publication) {
        return new PublicationResponse(
                publication.getUuid(),
                publication.getTitle(),
                publication.getDescription(),
                publication.getPublishDate(),
                publication.getFileUpload(),
                publication.isPublishStatus(),
                publication.getCreatedBy().getUuid(),
                publication.getCreatedAt(),
                publication.getUpdatedAt()
        );
    }
}
