package com.kasi.service;

import com.kasi.domain.UserRole;
import com.kasi.payload.dto.UserDTO;

import java.util.List;

public interface EmployeeService {
    UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception;

    UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception;

    UserDTO updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception;

    void deleteEmployee(Long employeeId) throws Exception;

    List<UserDTO> findStoreEmployees(Long storeId, UserRole role) throws Exception;

    List<UserDTO> findBranchEmployees(Long branchId, UserRole role) throws Exception;
}
