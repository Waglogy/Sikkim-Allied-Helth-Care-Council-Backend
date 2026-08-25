package in.sikkim_allied_health_care_council.server.controllers;

import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.submissions.CreateGrievance;
import in.sikkim_allied_health_care_council.server.dto.submissions.GrievanceResponse;
import in.sikkim_allied_health_care_council.server.dto.submissions.UpdateResolved;
import in.sikkim_allied_health_care_council.server.services.submissions.GrievanceService;
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
 * Public grievance form. {@code POST} is public (visitors submit grievances);
 * listing / resolving / deleting require an authenticated admin.
 */
@RestController
@RequestMapping("/api/grievance")
public class GrievanceController {

    private final GrievanceService grievanceService;

    public GrievanceController(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GrievanceResponse>> createGrievance(
            @Valid @RequestBody CreateGrievance createGrievanceRequest
    ) {
        GrievanceResponse created = grievanceService.createGrievance(createGrievanceRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Your grievance has been received.", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GrievanceResponse>>> getAllGrievances() {
        return ResponseEntity
                .ok(ApiResponse.success("Grievances fetched successfully", grievanceService.getAllGrievances()));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ApiResponse<GrievanceResponse>> setResolved(
            @PathVariable UUID uuid,
            @RequestBody UpdateResolved request
    ) {
        GrievanceResponse updated = grievanceService.setResolved(uuid, request.resolved());
        return ResponseEntity
                .ok(ApiResponse.success("Grievance updated successfully", updated));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deleteGrievance(
            @PathVariable UUID uuid
    ) {
        grievanceService.deleteGrievance(uuid);
        return ResponseEntity
                .ok(ApiResponse.success("Grievance deleted successfully"));
    }
}
