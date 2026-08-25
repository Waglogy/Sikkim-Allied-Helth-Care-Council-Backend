package in.sikkim_allied_health_care_council.server.controllers;

import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.members.CategoryResponse;
import in.sikkim_allied_health_care_council.server.dto.members.CreateCategory;
import in.sikkim_allied_health_care_council.server.services.members.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CreateCategory createCategoryRequest
    ) {
        CategoryResponse created = categoryService.createCategory(createCategoryRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Category created successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity
                .ok(ApiResponse.success("Categories fetched successfully", categories));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(
            @PathVariable UUID uuid
    ) {
        CategoryResponse category = categoryService.getCategoryByUuid(uuid);
        return ResponseEntity
                .ok(ApiResponse.success("Category fetched successfully", category));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable UUID uuid,
            @Valid @RequestBody CreateCategory updateCategoryRequest
    ) {
        CategoryResponse updated = categoryService.updateCategory(uuid, updateCategoryRequest);
        return ResponseEntity
                .ok(ApiResponse.success("Category updated successfully", updated));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable UUID uuid
    ) {
        categoryService.deleteCategory(uuid);
        return ResponseEntity
                .ok(ApiResponse.success("Category deleted successfully"));
    }
}
