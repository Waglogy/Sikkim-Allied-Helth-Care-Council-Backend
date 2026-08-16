package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.announcements.CreateEvent;
import in.sikkim_allied_health_care_council.server.dto.announcements.EventResponse;
import in.sikkim_allied_health_care_council.server.services.announcements.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
