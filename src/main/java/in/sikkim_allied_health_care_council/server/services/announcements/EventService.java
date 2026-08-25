package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreateEvent;
import in.sikkim_allied_health_care_council.server.dto.announcements.EventResponse;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Events;
import in.sikkim_allied_health_care_council.server.repositories.EventRepository;
import in.sikkim_allied_health_care_council.server.security.CurrentAdminProvider;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CurrentAdminProvider currentAdminProvider;

    public EventService(EventRepository eventRepository, CurrentAdminProvider currentAdminProvider) {
        this.eventRepository = eventRepository;
        this.currentAdminProvider = currentAdminProvider;
    }

    public EventResponse createEvent(@Valid CreateEvent createEvent) {

        AdminUser createdBy = currentAdminProvider.getCurrentAdmin();

        Events event = Events.builder()
                .name(createEvent.name())
                .description(createEvent.description())
                .eventDate(createEvent.eventDate())
                .images(createEvent.images())
                .documents(createEvent.documents())
                .createdBy(createdBy)
                .build();

        return EventResponse.from(eventRepository.save(event));
    }
}
