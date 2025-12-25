package com.kasi.service.impl;

import com.kasi.domain.UserRole;
import com.kasi.mapper.CategoryMapper;
import com.kasi.model.Category;
import com.kasi.model.Store;
import com.kasi.model.User;
import com.kasi.payload.dto.CategoryDTO;
import com.kasi.repository.CategoryRepository;
import com.kasi.repository.StoreRepository;
import com.kasi.service.CategoryService;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception {
        User user = userService.getCurrentUser();

        Store store = storeRepository.findById(categoryDTO.getStoreId())
                .orElseThrow(() -> new Exception("Store Not found"));
        Category category = Category.builder()
                .store(store)
//                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .build();
        checkAuthority(user, category.getStore());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getCategoriesByStore(Long storeId) {
        List<Category> category = categoryRepository.findByStoreId(storeId);
        return category.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("Category Not found"));
        User user = userService.getCurrentUser();
        checkAuthority(user, category.getStore());
        category.setName(categoryDTO.getName());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("Category Not found"));
        User user = userService.getCurrentUser();
        checkAuthority(user, category.getStore());
        categoryRepository.delete(category);
    }

    private void checkAuthority(User user, Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if (!(isAdmin && isSameStore) && !isManager) {
            throw new Exception("You don't have permission to manage this category");
        }

    }

}