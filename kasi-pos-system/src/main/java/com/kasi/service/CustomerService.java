package com.kasi.service;

import com.kasi.payload.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws Exception;

    void deleteCustomer(Long id) throws Exception;

    CustomerDTO getCustomerById(Long id) throws Exception;

    List<CustomerDTO> getAllCustomers();

    List<CustomerDTO> searchCustomers(String keyword);

}
