package in.sikkim_allied_health_care_council.server.controllers;

import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.submissions.ContactResponse;
import in.sikkim_allied_health_care_council.server.dto.submissions.CreateContact;
import in.sikkim_allied_health_care_council.server.dto.submissions.UpdateResolved;
import in.sikkim_allied_health_care_council.server.services.submissions.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Contact form. {@code POST} is public (website visitors submit enquiries);
 * listing / resolving / deleting require an authenticated admin.
 */
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(
            @Valid @RequestBody CreateContact createContactRequest
    ) {
        ContactResponse created = contactService.createContact(createContactRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Your message has been received.", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactResponse>>> getAllContacts() {
        return ResponseEntity
                .ok(ApiResponse.success("Contact messages fetched successfully", contactService.getAllContacts()));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ContactResponse>> setResolved(
            @PathVariable UUID uuid,
            @RequestBody UpdateResolved request
    ) {
        ContactResponse updated = contactService.setResolved(uuid, request.resolved());
        return ResponseEntity
                .ok(ApiResponse.success("Contact message updated successfully", updated));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deleteContact(
            @PathVariable UUID uuid
    ) {
        contactService.deleteContact(uuid);
        return ResponseEntity
                .ok(ApiResponse.success("Contact message deleted successfully"));
    }
}
