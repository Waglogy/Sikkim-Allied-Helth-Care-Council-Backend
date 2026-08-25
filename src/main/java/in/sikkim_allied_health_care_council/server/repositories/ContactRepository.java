package in.sikkim_allied_health_care_council.server.repositories;

import in.sikkim_allied_health_care_council.server.models.submissions.ContactSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<ContactSubmission, Long> {

    Optional<ContactSubmission> findByUuid(UUID uuid);

    List<ContactSubmission> findAllByOrderByCreatedAtDesc();
}
