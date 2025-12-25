package com.kasi.mapper;

import com.kasi.model.Category;
import com.kasi.payload.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
//                .store(category.getStore())
                .storeId(category.getStore() != null ? category.getStore().getId() : null)
                .build();
    }
}

