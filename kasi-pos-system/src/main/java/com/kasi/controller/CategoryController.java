package com.kasi.controller;

import com.kasi.payload.dto.CategoryDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestBody CategoryDTO categoryDTO) throws Exception {
        try {
            return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
        } catch (Exception e) {
            if (e.getMessage().contains("permission")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(null);
            }
            throw e;
        }
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDTO>> getCategories(
            @PathVariable Long storeId) throws Exception {
        return ResponseEntity.ok(categoryService.getCategoriesByStore(storeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategories(
            @PathVariable Long id, @RequestBody CategoryDTO categoryDTO) throws Exception {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteCategories(
            @PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category Delete Successfully");
        return ResponseEntity.ok(apiResponse);
    }
}

