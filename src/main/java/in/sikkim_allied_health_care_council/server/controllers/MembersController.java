package in.sikkim_allied_health_care_council.server.controllers;


import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.members.CreateMember;
import in.sikkim_allied_health_care_council.server.dto.members.MemberResponse;
import in.sikkim_allied_health_care_council.server.services.members.MembersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {

        List<MemberResponse> members = membersService.getAllMembers();

        return ResponseEntity
                .ok(ApiResponse.success("Members fetched successfully", members));

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(
            @PathVariable UUID uuid
    ) {

        MemberResponse member = membersService.getMemberByUuid(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Member fetched successfully", member));

    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMember(
            @PathVariable UUID uuid,
            @Valid @RequestBody CreateMember updateMemberRequest
    ) {

        MemberResponse updated = membersService.updateMember(uuid, updateMemberRequest);

        return ResponseEntity
                .ok(ApiResponse.success("Member updated successfully", updated));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable UUID uuid
    ) {

        membersService.deleteMember(uuid);

        return ResponseEntity
                .ok(ApiResponse.success("Member deleted successfully"));

    }

}
