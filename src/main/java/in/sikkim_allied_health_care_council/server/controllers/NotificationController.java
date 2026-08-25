package in.sikkim_allied_health_care_council.server.controllers;

import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.notifications.CreateNotification;
import in.sikkim_allied_health_care_council.server.dto.notifications.NotificationResponse;
import in.sikkim_allied_health_care_council.server.services.notifications.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(
            @Valid @RequestBody CreateNotification createNotificationRequest
    ) {

        NotificationResponse created = notificationService.createNotification(createNotificationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification created successfully", created));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications() {

        List<NotificationResponse> notifications = notificationService.getAllNotifications();

        return ResponseEntity
                .ok(ApiResponse.success("Notifications fetched successfully", notifications));

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotification(
            @PathVariable UUID uuid
    ) {

        NotificationResponse notification = notificationService.getNotificationByUuid(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Notification fetched successfully", notification));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<NotificationResponse>> updateNotification(
            @PathVariable UUID uuid,
            @Valid @RequestBody CreateNotification updateNotificationRequest
    ) {

        NotificationResponse updated = notificationService.updateNotification(uuid, updateNotificationRequest);

        return ResponseEntity
                .ok(ApiResponse.success("Notification updated successfully", updated));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @PathVariable UUID uuid
    ) {

        notificationService.deleteNotification(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Notification deleted successfully"));

    }
}
