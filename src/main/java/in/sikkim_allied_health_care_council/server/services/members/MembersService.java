package in.sikkim_allied_health_care_council.server.services.members;

import in.sikkim_allied_health_care_council.server.dto.members.CreateMember;
import in.sikkim_allied_health_care_council.server.dto.members.MemberResponse;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import in.sikkim_allied_health_care_council.server.models.members.Category;
import in.sikkim_allied_health_care_council.server.models.members.Members;
import in.sikkim_allied_health_care_council.server.repositories.CategoryRepository;
import in.sikkim_allied_health_care_council.server.repositories.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MembersService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public MembersService(MemberRepository memberRepository, CategoryRepository categoryRepository) {
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    public MemberResponse createMember(@Valid CreateMember createMember) {

        Members member = Members.builder()
                .name(createMember.name())
                .designation(createMember.designation())
                .photo(createMember.photo())
                .profileDescription(createMember.description())
                .orderPriority(createMember.priority())
                .category(resolveCategory(createMember.categoryUuid()))
                .build();

        return MemberResponse.from(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAllByOrderByOrderPriorityAscNameAsc()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MemberResponse getMemberByUuid(UUID uuid) {
        Members member = memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found."));
        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse updateMember(UUID uuid, @Valid CreateMember updateMember) {
        Members member = memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found."));

        member.setName(updateMember.name());
        member.setDesignation(updateMember.designation());
        member.setPhoto(updateMember.photo());
        member.setProfileDescription(updateMember.description());
        member.setOrderPriority(updateMember.priority());
        member.setCategory(resolveCategory(updateMember.categoryUuid()));

        return MemberResponse.from(memberRepository.save(member));
    }

    @Transactional
    public void deleteMember(UUID uuid) {
        Members member = memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found."));
        memberRepository.delete(member);
    }

    /** Resolves a category by UUID, or null when none is supplied. */
    private Category resolveCategory(UUID categoryUuid) {
        if (categoryUuid == null) {
            return null;
        }
        return categoryRepository.findByUuid(categoryUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    }
}
