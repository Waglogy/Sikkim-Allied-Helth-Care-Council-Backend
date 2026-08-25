package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.announcements.CreateEvent;
import in.sikkim_allied_health_care_council.server.dto.announcements.EventResponse;
import in.sikkim_allied_health_care_council.server.services.announcements.EventService;
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
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(
            @Valid @RequestBody CreateEvent createEventRequest
    ) {

        EventResponse created = eventService.createEvent(createEventRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Event created successfully", created));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvents() {

        List<EventResponse> events = eventService.getAllEvents();

        return ResponseEntity
                .ok(ApiResponse.success("Events fetched successfully", events));

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<EventResponse>> getEvent(
            @PathVariable UUID uuid
    ) {

        EventResponse event = eventService.getEventByUuid(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Event fetched successfully", event));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(
            @PathVariable UUID uuid,
            @Valid @RequestBody CreateEvent updateEventRequest
    ) {

        EventResponse updated = eventService.updateEvent(uuid, updateEventRequest);

        return ResponseEntity
                .ok(ApiResponse.success("Event updated successfully", updated));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(
            @PathVariable UUID uuid
    ) {

        eventService.deleteEvent(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Event deleted successfully"));

    }

}
