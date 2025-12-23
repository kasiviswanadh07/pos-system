package com.kasi.mapper;

import com.kasi.model.Product;
import com.kasi.model.Store;
import com.kasi.payload.dto.ProductDTO;

import java.time.LocalDateTime;

public class ProductMapper {

    private LocalDateTime updateAt;

    public static  ProductDTO toDTO(Product product) {
        return ProductDTO.builder().id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .brand(product.getBrand())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .storeId(product.getStore() != null ? product.getStore().getId() : null)
                .image(product.getImage())
                .createAt(product.getCreateAt())
                .updateAt(product.getUpdateAt())
                .build();
//                        .categoryId(product.getca)

    }

    public static  Product toEntity(ProductDTO productDTO, Store store) {
        return Product.builder().name(productDTO.getName())
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .mrp(productDTO.getMrp())
                .sellingPrice(productDTO.getSellingPrice())
                .brand(productDTO.getBrand())
                .build();
    }

}
