package in.sikkim_allied_health_care_council.server.repositories;

import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser> findByEmail(String email);

}
