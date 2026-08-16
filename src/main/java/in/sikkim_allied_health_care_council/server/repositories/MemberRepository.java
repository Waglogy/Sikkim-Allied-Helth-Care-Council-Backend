package in.sikkim_allied_health_care_council.server.repositories;

import in.sikkim_allied_health_care_council.server.models.members.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {
}
