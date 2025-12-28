package com.kasi.service;

import com.kasi.payload.dto.CategoryDTO;

import java.util.List;
public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception;

    List<CategoryDTO> getCategoriesByStore(Long storeId);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws Exception;

    void deleteCategory(Long id) throws Exception;
}
