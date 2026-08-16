package in.sikkim_allied_health_care_council.server.services.members;

import in.sikkim_allied_health_care_council.server.dto.members.CreateMember;
import in.sikkim_allied_health_care_council.server.dto.members.MemberResponse;
import in.sikkim_allied_health_care_council.server.models.members.Members;
import in.sikkim_allied_health_care_council.server.repositories.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class MembersService {

    private final MemberRepository memberRepository;

    public MembersService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(@Valid CreateMember createMember) {

        Members member = Members.builder()
                .name(createMember.name())
                .designation(createMember.designation())
                .build();

        return MemberResponse.from(memberRepository.save(member));
    }
}
