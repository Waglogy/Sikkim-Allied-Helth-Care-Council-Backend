package in.sikkim_allied_health_care_council.server.dto.members;

import in.sikkim_allied_health_care_council.server.models.members.Category;
import in.sikkim_allied_health_care_council.server.models.members.CategoryType;

import java.util.List;
import java.util.UUID;

/**
 * A category plus the members listed under it (ordered by member priority).
 * Used for the grouped council view.
 */
public record CategoryResponse(
        UUID uuid,
        String name,
        int priority,
        CategoryType type,
        List<MemberResponse> members
) {

    public static CategoryResponse from(Category category, List<MemberResponse> members) {
        return new CategoryResponse(
                category.getUuid(),
                category.getName(),
                category.getPriority(),
                category.getType(),
                members
        );
    }
}
