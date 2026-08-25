package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.announcements.CreatePublication;
import in.sikkim_allied_health_care_council.server.dto.announcements.PublicationResponse;
import in.sikkim_allied_health_care_council.server.services.announcements.PublicationService;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<PublicationResponse>>> getAllPublications() {

        List<PublicationResponse> publications = publicationService.getAllPublications();

        return ResponseEntity
                .ok(ApiResponse.success("Publications fetched successfully", publications));

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<PublicationResponse>> getPublication(
            @PathVariable UUID uuid
    ) {

        PublicationResponse publication = publicationService.getPublicationByUuid(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Publication fetched successfully", publication));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<PublicationResponse>> updatePublication(
            @PathVariable UUID uuid,
            @Valid @RequestBody CreatePublication updatePublicationRequest
    ) {

        PublicationResponse updated = publicationService.updatePublication(uuid, updatePublicationRequest);

        return ResponseEntity
                .ok(ApiResponse.success("Publication updated successfully", updated));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deletePublication(
            @PathVariable UUID uuid
    ) {

        publicationService.deletePublication(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Publication deleted successfully"));

    }

}
