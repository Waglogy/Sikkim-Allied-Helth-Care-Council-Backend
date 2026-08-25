package in.sikkim_allied_health_care_council.server.services.announcements;

import in.sikkim_allied_health_care_council.server.dto.announcements.CreatePublication;
import in.sikkim_allied_health_care_council.server.dto.announcements.PublicationResponse;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.models.announcements.Publications;
import in.sikkim_allied_health_care_council.server.repositories.PublicationRepository;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.security.CurrentAdminProvider;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public List<PublicationResponse> getAllPublications() {
        return publicationRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PublicationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PublicationResponse getPublicationByUuid(UUID uuid) {
        Publications publication = publicationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found."));
        return PublicationResponse.from(publication);
    }

    @Transactional
    public PublicationResponse updatePublication(UUID uuid, @Valid CreatePublication updatePublication) {
        Publications publication = publicationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found."));

        publication.setTitle(updatePublication.title());
        publication.setDescription(updatePublication.description());
        publication.setPublishDate(updatePublication.publishDate());
        publication.setFileUpload(updatePublication.fileUpload());
        publication.setPublishStatus(updatePublication.publishStatus());

        return PublicationResponse.from(publicationRepository.save(publication));
    }

    @Transactional
    public void deletePublication(UUID uuid) {
        Publications publication = publicationRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Publication not found."));
        publicationRepository.delete(publication);
    }
}
