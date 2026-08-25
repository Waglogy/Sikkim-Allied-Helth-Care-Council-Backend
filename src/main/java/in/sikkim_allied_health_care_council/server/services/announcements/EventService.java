package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreateEvent;
import in.sikkim_allied_health_care_council.server.dto.announcements.EventResponse;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Events;
import in.sikkim_allied_health_care_council.server.repositories.EventRepository;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.security.CurrentAdminProvider;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAllByOrderByEventDateDesc()
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventResponse getEventByUuid(UUID uuid) {
        Events event = eventRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found."));
        return EventResponse.from(event);
    }

    @Transactional
    public EventResponse updateEvent(UUID uuid, @Valid CreateEvent updateEvent) {
        Events event = eventRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found."));

        event.setName(updateEvent.name());
        event.setDescription(updateEvent.description());
        event.setEventDate(updateEvent.eventDate());
        event.setImages(updateEvent.images());
        event.setDocuments(updateEvent.documents());

        return EventResponse.from(eventRepository.save(event));
    }

    @Transactional
    public void deleteEvent(UUID uuid) {
        Events event = eventRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found."));
        eventRepository.delete(event);
    }
}
