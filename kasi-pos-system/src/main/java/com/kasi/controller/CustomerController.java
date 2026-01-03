package com.kasi.controller;

import com.kasi.payload.dto.CustomerDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {

        return ResponseEntity.ok(customerService.createCustomer(customerDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,
                                                      @RequestBody CustomerDTO customerDTO) throws Exception {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
    }

    @DeleteMapping("{id}")
    public ApiResponse deleteCustomer(@PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Customer Deleted Successfully");
        return apiResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws Exception {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomer(@RequestParam (required = false)String keyword,
                                                            @RequestParam (required = false)String email) throws Exception {
        return ResponseEntity.ok(customerService.searchCustomers(keyword, email));
    }

}
