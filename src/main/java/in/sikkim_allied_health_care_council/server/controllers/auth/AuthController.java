package in.sikkim_allied_health_care_council.server.controllers.auth;

import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.auth.AdminResponse;
import in.sikkim_allied_health_care_council.server.dto.auth.CreateAdmin;
import in.sikkim_allied_health_care_council.server.services.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    ResponseEntity<ApiResponse<AdminResponse>> adminSignup(
            @Valid @RequestBody CreateAdmin admin
    ) {

        AdminResponse created = authService.signupAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Admin signup successful.", created));
    }

}
