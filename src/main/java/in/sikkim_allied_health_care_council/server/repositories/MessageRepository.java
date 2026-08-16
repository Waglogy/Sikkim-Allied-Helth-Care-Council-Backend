package in.sikkim_allied_health_care_council.server.repositories;

import in.sikkim_allied_health_care_council.server.models.announcements.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {
}
