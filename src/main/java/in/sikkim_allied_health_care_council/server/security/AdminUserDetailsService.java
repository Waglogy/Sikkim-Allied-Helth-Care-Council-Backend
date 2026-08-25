package in.sikkim_allied_health_care_council.server.security;

import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.repositories.AdminUserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminUserRepository adminUserRepository;

    public AdminUserDetailsService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        AdminUser admin = adminUserRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Admin user not found"
                        )
                );

        return User.withUsername(admin.getName())
                .password(admin.getPassword())
                .roles(admin.getRole().name())
                .build();
    }
}
