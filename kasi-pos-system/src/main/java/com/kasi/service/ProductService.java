package com.kasi.service;

import com.kasi.model.User;
import com.kasi.payload.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception;

    ProductDTO getProduct(ProductDTO productDTO, User user);

    ProductDTO updateProduct(Long id, ProductDTO productDTO, User user) throws Exception;

    void deleteProduct(Long id, User user) throws Exception;

    List<ProductDTO> getProductsByStoreId(Long storeId) throws Exception;

    List<ProductDTO> searchByKeyword(Long storeId, String keyword);
}
