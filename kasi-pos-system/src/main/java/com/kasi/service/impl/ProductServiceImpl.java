package com.kasi.service.impl;

import com.kasi.mapper.ProductMapper;
import com.kasi.model.Category;
import com.kasi.model.Product;
import com.kasi.model.Store;
import com.kasi.model.User;
import com.kasi.payload.dto.ProductDTO;
import com.kasi.repository.CategoryRepository;
import com.kasi.repository.ProductRepository;
import com.kasi.repository.StoreRepository;
import com.kasi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception {
        Store store = storeRepository.findById(productDTO.getStoreId()
        ).orElseThrow(
                () -> new Exception("Store not found"));
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new Exception("Category Not Found")
        );
        Product product = ProductMapper.toEntity(productDTO, store, category);

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO getProduct(ProductDTO productDTO, User user) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, User user) throws Exception {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new Exception("Product not found"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSku(productDTO.getSku());
        product.setImage(productDTO.getImage());
        product.setMrp(productDTO.getMrp());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setBrand(productDTO.getBrand());
        product.setUpdateAt(LocalDateTime.now());
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                    () -> new Exception("Category Not Found")
            );
            product.setCategory(category);
        }
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
        Product product = productRepository.findById(id).
                orElseThrow(
                        () -> new Exception("Product not found")
                );
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getProductsByStoreId(Long storeId) throws Exception {
        List<Product> products = productRepository.findByStoreId(storeId);
        if (products.isEmpty()) {
            throw new Exception("Product not found with store id: " + storeId);
        }
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
