package com.kasi.service;

import com.kasi.exception.UserException;
import com.kasi.payload.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception;

    List<CategoryDTO> getCategoriesByStore(Long storeId);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws Exception;

    void deleteCategory(Long id) throws Exception;
}
