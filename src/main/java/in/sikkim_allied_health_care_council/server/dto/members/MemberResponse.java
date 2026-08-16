package in.sikkim_allied_health_care_council.server.dto.members;

import in.sikkim_allied_health_care_council.server.models.members.Members;

import java.util.UUID;

/**
 * Client-facing view of a {@link Members} entity. Exposes only safe fields and
 * uses the public {@code uuid} rather than the internal database {@code id}.
 */
public record MemberResponse(
        UUID uuid,
        String name,
        String designation,
        String photo,
        String profileDescription,
        int orderPriority
) {

    public static MemberResponse from(Members member) {
        return new MemberResponse(
                member.getUuid(),
                member.getName(),
                member.getDesignation(),
                member.getPhoto(),
                member.getProfileDescription(),
                member.getOrderPriority()
        );
    }
}
