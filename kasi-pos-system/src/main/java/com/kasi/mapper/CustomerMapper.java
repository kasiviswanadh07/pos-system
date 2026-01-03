package com.kasi.mapper;

import com.kasi.model.Customer;
import com.kasi.payload.dto.CustomerDTO;

public class CustomerMapper {
    public static CustomerDTO toDto(Customer customer) {

        return CustomerDTO.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public static Customer toEntity(CustomerDTO customerDTO) {

        return Customer.builder()
                .id(customerDTO.getId())
                .fullName(customerDTO.getFullName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .updatedAt(customerDTO.getUpdatedAt())
                .createdAt(customerDTO.getCreatedAt())
                .build();
    }
}
