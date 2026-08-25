package in.sikkim_allied_health_care_council.server.dto.notifications;

import in.sikkim_allied_health_care_council.server.models.notifications.Notification;

import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(
        UUID uuid,
        String title,
        String document,
        Instant createdAt,
        Instant updatedAt
) {

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getUuid(),
                notification.getTitle(),
                notification.getDocument(),
                notification.getCreatedAt(),
                notification.getUpdatedAt()
        );
    }
}
