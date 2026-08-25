package in.sikkim_allied_health_care_council.server.services.submissions;

import in.sikkim_allied_health_care_council.server.dto.submissions.CreateGrievance;
import in.sikkim_allied_health_care_council.server.dto.submissions.GrievanceResponse;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.submissions.Grievance;
import in.sikkim_allied_health_care_council.server.repositories.GrievanceRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GrievanceService {

    private final GrievanceRepository grievanceRepository;

    public GrievanceService(GrievanceRepository grievanceRepository) {
        this.grievanceRepository = grievanceRepository;
    }

    public GrievanceResponse createGrievance(@Valid CreateGrievance createGrievance) {
        Grievance grievance = Grievance.builder()
                .name(createGrievance.name())
                .email(createGrievance.email())
                .phone(createGrievance.phone())
                .registrationNumber(createGrievance.registrationNumber())
                .category(createGrievance.category())
                .subject(createGrievance.subject())
                .message(createGrievance.message())
                .resolved(false)
                .build();
        return GrievanceResponse.from(grievanceRepository.save(grievance));
    }

    @Transactional(readOnly = true)
    public List<GrievanceResponse> getAllGrievances() {
        return grievanceRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(GrievanceResponse::from)
                .toList();
    }

    @Transactional
    public GrievanceResponse setResolved(UUID uuid, boolean resolved) {
        Grievance grievance = grievanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Grievance not found."));
        grievance.setResolved(resolved);
        return GrievanceResponse.from(grievanceRepository.save(grievance));
    }

    @Transactional
    public void deleteGrievance(UUID uuid) {
        Grievance grievance = grievanceRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Grievance not found."));
        grievanceRepository.delete(grievance);
    }
}
