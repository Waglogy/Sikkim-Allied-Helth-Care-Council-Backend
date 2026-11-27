package in.sikkim_allied_health_care_council.server.services.notifications;

import in.sikkim_allied_health_care_council.server.dto.notifications.CreateNotification;
import in.sikkim_allied_health_care_council.server.dto.notifications.NotificationResponse;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.notifications.Notification;
import in.sikkim_allied_health_care_council.server.repositories.NotificationRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationResponse createNotification(@Valid CreateNotification createNotification) {

        Notification notification = Notification.builder()
                .title(createNotification.title())
                .document(createNotification.document())
                .date(resolveDate(createNotification.date()))
                .build();

        return NotificationResponse.from(notificationRepository.save(notification));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(NotificationResponse::from)
                // Newest publication date first; createdAt order (already desc) breaks ties.
                .sorted(Comparator.comparing(NotificationResponse::date).reversed())
                .toList();
    }

    @Transactional(readOnly = true)
    public NotificationResponse getNotificationByUuid(UUID uuid) {
        Notification notification = notificationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found."));
        return NotificationResponse.from(notification);
    }

    @Transactional
    public NotificationResponse updateNotification(UUID uuid, @Valid CreateNotification updateNotification) {
        Notification notification = notificationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found."));

        notification.setTitle(updateNotification.title());
        notification.setDocument(updateNotification.document());
        notification.setDate(resolveDate(updateNotification.date()));

        return NotificationResponse.from(notificationRepository.save(notification));
    }

    @Transactional
    public void deleteNotification(UUID uuid) {
        Notification notification = notificationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found."));
        notificationRepository.delete(notification);
    }

    private static LocalDate resolveDate(LocalDate date) {
        return date != null ? date : LocalDate.now(ZoneId.of("Asia/Kolkata"));
    }
}
