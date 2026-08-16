package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.announcements.CreatePublication;
import in.sikkim_allied_health_care_council.server.dto.announcements.PublicationResponse;
import in.sikkim_allied_health_care_council.server.services.announcements.PublicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publication")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<PublicationResponse>> createPublication(
            @Valid @RequestBody CreatePublication createPublicationRequest
    ) {

        PublicationResponse created = publicationService.createPublication(createPublicationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Publication created successfully", created));

    }

}
