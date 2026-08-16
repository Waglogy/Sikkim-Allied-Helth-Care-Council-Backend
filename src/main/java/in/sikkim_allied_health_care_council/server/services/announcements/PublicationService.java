package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreatePublication;
import in.sikkim_allied_health_care_council.server.dto.announcements.PublicationResponse;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Publications;
import in.sikkim_allied_health_care_council.server.repositories.AdminUserRepository;
import in.sikkim_allied_health_care_council.server.repositories.PublicationRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final AdminUserRepository adminUserRepository;

    public PublicationService(PublicationRepository publicationRepository, AdminUserRepository adminUserRepository) {
        this.publicationRepository = publicationRepository;
        this.adminUserRepository = adminUserRepository;
    }

    public PublicationResponse createPublication(@Valid CreatePublication createPublication) {

        AdminUser createdBy = adminUserRepository.findByUuid(createPublication.createdBy())
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found."));

        Publications publication = Publications.builder()
                .title(createPublication.title())
                .description(createPublication.description())
                .publishDate(createPublication.publishDate())
                .fileUpload(createPublication.fileUpload())
                .publishStatus(createPublication.publishStatus())
                .createdBy(createdBy)
                .build();

        return PublicationResponse.from(publicationRepository.save(publication));
    }
}
