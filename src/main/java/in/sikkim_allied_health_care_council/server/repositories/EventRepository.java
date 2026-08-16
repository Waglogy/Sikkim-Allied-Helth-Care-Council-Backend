package in.sikkim_allied_health_care_council.server.repositories;

import in.sikkim_allied_health_care_council.server.models.announcements.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {
}
