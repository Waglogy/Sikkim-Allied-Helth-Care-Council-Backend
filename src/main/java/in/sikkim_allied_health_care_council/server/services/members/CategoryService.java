package in.sikkim_allied_health_care_council.server.services.members;

import in.sikkim_allied_health_care_council.server.dto.members.CategoryResponse;
import in.sikkim_allied_health_care_council.server.dto.members.CreateCategory;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public CategoryService(CategoryRepository categoryRepository, MemberRepository memberRepository) {
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
    }

    public CategoryResponse createCategory(@Valid CreateCategory createCategory) {
        Category category = Category.builder()
                .name(createCategory.name())
                .priority(createCategory.priority())
                .type(createCategory.type())
                .build();
        return CategoryResponse.from(categoryRepository.save(category), List.of());
    }

    /** All categories (ordered), each with the members listed under it. */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepository.findAllByOrderByTypeAscPriorityAscNameAsc();

        Map<UUID, List<MemberResponse>> membersByCategory = memberRepository
                .findAllByOrderByOrderPriorityAscNameAsc()
                .stream()
                .filter(m -> m.getCategory() != null)
                .collect(Collectors.groupingBy(
                        m -> m.getCategory().getUuid(),
                        Collectors.mapping(MemberResponse::from, Collectors.toList())
                ));

        return categories.stream()
                .map(c -> CategoryResponse.from(c, membersByCategory.getOrDefault(c.getUuid(), List.of())))
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryByUuid(UUID uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        List<MemberResponse> members = memberRepository
                .findByCategory_UuidOrderByOrderPriorityAscNameAsc(uuid)
                .stream()
                .map(MemberResponse::from)
                .toList();
        return CategoryResponse.from(category, members);
    }

    @Transactional
    public CategoryResponse updateCategory(UUID uuid, @Valid CreateCategory updateCategory) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        category.setName(updateCategory.name());
        category.setPriority(updateCategory.priority());
        category.setType(updateCategory.type());
        return CategoryResponse.from(categoryRepository.save(category), List.of());
    }

    @Transactional
    public void deleteCategory(UUID uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        // Detach members instead of deleting them — they become uncategorised.
        List<Members> members = memberRepository.findByCategory_UuidOrderByOrderPriorityAscNameAsc(uuid);
        members.forEach(m -> m.setCategory(null));
        memberRepository.saveAll(members);

        categoryRepository.delete(category);
    }
}
