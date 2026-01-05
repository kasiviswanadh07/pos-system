package com.kasi.service.impl;

import com.kasi.domain.UserRole;
import com.kasi.mapper.UserMapper;
import com.kasi.model.Branch;
import com.kasi.model.Store;
import com.kasi.model.User;
import com.kasi.payload.dto.UserDTO;
import com.kasi.repository.BranchRepository;
import com.kasi.repository.StoreRepository;
import com.kasi.repository.UserRepository;
import com.kasi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeSevicesImpl implements EmployeeService {
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new Exception("Store Not found"));
        Branch branch = null;
        if (employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            if (employee.getBranchId() == null)
                throw new Exception("Branch id is required to create Branch manager");
            branch = branchRepository.findById(employee.getBranchId()).orElseThrow(
                    () -> new Exception("Branch Not Found")
            );
        }
        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User savedEmployee = userRepository.save(user);
        if (employee.getRole() == UserRole.ROLE_STORE_MANAGER && branch != null) {
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new Exception("Branch Not Found"));
        if (employee.getRole() == UserRole.ROLE_BRANCH_CASHIER
                || employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }
        throw new Exception("Branch Role Not supported");
    }

    @Override
    public UserDTO updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception {
        Branch branch = branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(
                () -> new Exception("Branch Not Found"));
        User existingEmployee = userRepository.findById(employeeId).orElseThrow(
                () -> new Exception("Employee not exist with given id"));
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFullname(employeeDetails.getFullname());
        existingEmployee.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
        existingEmployee.setRole(employeeDetails.getRole());
        existingEmployee.setBranch(branch);
        User updateUser = userRepository.save(existingEmployee);
        return UserMapper.toDTO(updateUser);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId).orElseThrow(
                () -> new Exception("Employee Not Found"));
        userRepository.delete(employee);
    }

    @Override
    public List<UserDTO> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new Exception("Store Not found"));


        return userRepository.findByStore(store)
                .stream().filter(user -> role == null || user.getRole() == role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new Exception("Branch Not Found"));
        List<UserDTO> employee = userRepository.findByBranchId(branchId)
                .stream().filter(user -> role == null || user.getRole() == role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return employee;
    }

    @Override
    public void resetPassword(String email, String password) throws Exception {
        User existingEmployee = userRepository.findByEmail(email);
        if (password == null || password.isEmpty()) {
            throw new Exception("Password can't be null or empty");
        } else if (existingEmployee.equals("")) {
            throw new Exception("Email Not Found");
        }
        existingEmployee.setPassword(passwordEncoder.encode(password));
        userRepository.save(existingEmployee);
    }
}
