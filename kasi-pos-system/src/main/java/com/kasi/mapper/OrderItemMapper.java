package com.kasi.mapper;

import com.kasi.model.Order;
import com.kasi.model.OrderItem;
import com.kasi.payload.dto.OrderItemDTO;

public class OrderItemMapper {
    public static OrderItemDTO toDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .productId(orderItem.getProduct().getId())
                .product(ProductMapper.toDTO(orderItem.getProduct()))
                .build();
    }
}
