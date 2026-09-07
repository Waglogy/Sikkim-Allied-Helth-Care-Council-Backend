package in.sikkim_allied_health_care_council.server.repositories;

import in.sikkim_allied_health_care_council.server.models.announcements.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {

    Optional<Messages> findByUuid(UUID uuid);

    List<Messages> findAllByOrderByCreatedAtDesc();

    /** Ordered for display: lowest priority first, newest as tie-breaker. */
    List<Messages> findAllByOrderByOrderPriorityAscCreatedAtDesc();
}
