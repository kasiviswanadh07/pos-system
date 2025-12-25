package com.kasi.repository;

import com.kasi.model.Category;
import com.kasi.payload.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStoreId(long storeId);
}
