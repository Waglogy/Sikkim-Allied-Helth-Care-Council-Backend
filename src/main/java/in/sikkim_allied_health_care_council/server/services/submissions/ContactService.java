package in.sikkim_allied_health_care_council.server.services.submissions;

import in.sikkim_allied_health_care_council.server.dto.submissions.ContactResponse;
import in.sikkim_allied_health_care_council.server.dto.submissions.CreateContact;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.submissions.ContactSubmission;
import in.sikkim_allied_health_care_council.server.repositories.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactResponse createContact(@Valid CreateContact createContact) {
        ContactSubmission submission = ContactSubmission.builder()
                .name(createContact.name())
                .email(createContact.email())
                .subject(createContact.subject())
                .message(createContact.message())
                .resolved(false)
                .build();
        return ContactResponse.from(contactRepository.save(submission));
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContacts() {
        return contactRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ContactResponse::from)
                .toList();
    }

    @Transactional
    public ContactResponse setResolved(UUID uuid, boolean resolved) {
        ContactSubmission submission = contactRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Contact message not found."));
        submission.setResolved(resolved);
        return ContactResponse.from(contactRepository.save(submission));
    }

    @Transactional
    public void deleteContact(UUID uuid) {
        ContactSubmission submission = contactRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Contact message not found."));
        contactRepository.delete(submission);
    }
}
