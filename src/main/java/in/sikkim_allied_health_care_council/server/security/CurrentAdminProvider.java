package in.sikkim_allied_health_care_council.server.security;

import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.repositories.AdminUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentAdminProvider {

    private final AdminUserRepository adminUserRepository;

    public CurrentAdminProvider(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public AdminUser getCurrentAdmin() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found."));
    }
}
