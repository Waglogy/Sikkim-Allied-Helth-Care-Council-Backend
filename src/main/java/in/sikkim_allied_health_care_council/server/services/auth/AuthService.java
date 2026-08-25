package in.sikkim_allied_health_care_council.server.services.auth;

import in.sikkim_allied_health_care_council.server.dto.auth.AdminResponse;
import in.sikkim_allied_health_care_council.server.dto.auth.CreateAdmin;
import in.sikkim_allied_health_care_council.server.dto.auth.LoginRequest;
import in.sikkim_allied_health_care_council.server.dto.auth.LoginResponse;
import in.sikkim_allied_health_care_council.server.exceptions.DuplicateResourceException;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.admin.AdminUser;
import in.sikkim_allied_health_care_council.server.repositories.AuthRepository;
import in.sikkim_allied_health_care_council.server.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            AuthRepository authRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AdminResponse signupAdmin(@Valid CreateAdmin admin) {

        Optional<AdminUser> isAlreadyAdminUserExists = authRepository.findByEmail(admin.email());

        if (isAlreadyAdminUserExists.isPresent())
            throw new DuplicateResourceException("An admin with this email already exists.");

        String newEncodedPassword = passwordEncoder.encode(admin.password());

        AdminUser user = AdminUser.builder()
                .name(admin.name())
                .email(admin.email())
                .password(newEncodedPassword)
                .role(admin.role())
                .build();

        return AdminResponse.from(authRepository.save(user));
    }

    public LoginResponse login(@Valid LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        AdminUser admin = authRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found."));

        String token = jwtService.generateToken(admin);

        return new LoginResponse(token, AdminResponse.from(admin));
    }

}
