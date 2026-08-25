package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreatePublication;
import in.sikkim_allied_health_care_council.server.dto.announcements.PublicationResponse;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Publications;
import in.sikkim_allied_health_care_council.server.repositories.PublicationRepository;
import in.sikkim_allied_health_care_council.server.security.CurrentAdminProvider;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final CurrentAdminProvider currentAdminProvider;

    public PublicationService(PublicationRepository publicationRepository, CurrentAdminProvider currentAdminProvider) {
        this.publicationRepository = publicationRepository;
        this.currentAdminProvider = currentAdminProvider;
    }

    public PublicationResponse createPublication(@Valid CreatePublication createPublication) {

        AdminUser createdBy = currentAdminProvider.getCurrentAdmin();

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
