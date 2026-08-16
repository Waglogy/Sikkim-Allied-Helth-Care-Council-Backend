package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.members.CreateMember;
import in.sikkim_allied_health_care_council.server.dto.members.MemberResponse;
import in.sikkim_allied_health_care_council.server.services.members.MembersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(
            @Valid @RequestBody CreateMember createMemberRequest
    ) {

        MemberResponse created = membersService.createMember(createMemberRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Member created successfully", created));

    }

}
