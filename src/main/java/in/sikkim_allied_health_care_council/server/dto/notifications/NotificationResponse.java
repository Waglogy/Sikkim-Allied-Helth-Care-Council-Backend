package in.sikkim_allied_health_care_council.server.dto.notifications;

import in.sikkim_allied_health_care_council.server.models.notifications.Notification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

public record NotificationResponse(
        UUID uuid,
        String title,
        String document,
        LocalDate date,
        Instant createdAt,
        Instant updatedAt
) {

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getUuid(),
                notification.getTitle(),
                notification.getDocument(),
                notification.getDate() != null
                        ? notification.getDate()
                        : LocalDate.ofInstant(notification.getCreatedAt(), ZoneId.of("Asia/Kolkata")),
                notification.getCreatedAt(),
                notification.getUpdatedAt()
        );
    }
}
