package in.sikkim_allied_health_care_council.server.repositories;

import in.sikkim_allied_health_care_council.server.models.members.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

    Optional<Members> findByUuid(UUID uuid);

    List<Members> findAllByOrderByOrderPriorityAscNameAsc();

    List<Members> findByCategory_UuidOrderByOrderPriorityAscNameAsc(UUID categoryUuid);
}
