package com.kasi.service.impl;

import com.kasi.mapper.CustomerMapper;
import com.kasi.model.Customer;
import com.kasi.payload.dto.CustomerDTO;
import com.kasi.repository.CustomerRepository;
import com.kasi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        return CustomerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws Exception {
        Customer customerToupdate = customerRepository.findById(id).orElseThrow(
                () -> new Exception("Customer Not Found"));
        customerToupdate.setFullName(customerDTO.getFullName());
        customerToupdate.setPhone(customerDTO.getPhone());
        customerToupdate.setEmail(customerDTO.getEmail());
        return CustomerMapper.toDto(customerRepository.save(customerToupdate));
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer customerToDelete = customerRepository.findById(id).orElseThrow(
                () -> new Exception("Customer Not Found"));
        customerRepository.delete(customerToDelete);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new Exception("Customer Not Found"));
        return CustomerMapper.toDto(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        return allCustomers.stream().map(
                CustomerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> searchCustomers(String fullname) {
        List<Customer> allCustomers =
                customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(fullname);
        return allCustomers.stream().map(
                CustomerMapper::toDto).collect(Collectors.toList());
    }
}
