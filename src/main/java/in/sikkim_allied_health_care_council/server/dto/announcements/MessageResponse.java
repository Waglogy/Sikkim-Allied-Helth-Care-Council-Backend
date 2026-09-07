package in.sikkim_allied_health_care_council.server.dto.announcements;

import in.sikkim_allied_health_care_council.server.models.announcements.Messages;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponse(
        UUID uuid,
        String title,
        String description,
        LocalDateTime publishDate,
        String photo,
        String fileUpload,
        boolean publishStatus,
        int priority,
        UUID createdBy,
        Instant createdAt,
        Instant updatedAt
) {

    public static MessageResponse from(Messages message) {
        return new MessageResponse(
                message.getUuid(),
                message.getTitle(),
                message.getDescription(),
                message.getPublishDate(),
                message.getPhoto(),
                message.getFileUpload(),
                message.isPublishStatus(),
                message.getOrderPriority(),
                message.getCreatedBy().getUuid(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }
}
