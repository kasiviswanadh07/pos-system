package com.kasi.controller;

import com.kasi.domain.UserRole;
import com.kasi.payload.dto.UserDTO;
import com.kasi.payload.response.ApiResponse;
import com.kasi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO> createStoreEmployee(@RequestBody UserDTO userDTO,
                                                       @PathVariable Long storeId) throws Exception {
        UserDTO employee = employeeService.createStoreEmployee(userDTO, storeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO> createBranchEmployee(@RequestBody UserDTO userDTO,
                                                        @PathVariable Long branchId) throws Exception {
        UserDTO employee = employeeService.createBranchEmployee(userDTO, branchId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("{employeeId}")
    public ResponseEntity<UserDTO> updateEmployee(@PathVariable Long employeeId,
                                                  @RequestBody UserDTO userDTO) throws Exception {
        UserDTO employee = employeeService.updateEmployee(employeeId, userDTO);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id) throws Exception {
        employeeService.deleteEmployee(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Employee Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{Id}")
    public ResponseEntity<List<UserDTO>> storeEmployee(@PathVariable Long Id,
                                                       @RequestParam(required = false) UserRole userRole
    ) throws Exception {
        List<UserDTO> employee = employeeService.findStoreEmployees(Id, userRole);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/branch/{Id}")
    public ResponseEntity<List<UserDTO>> branchEmployee(@PathVariable Long Id,
                                                        @RequestParam(required = false) UserRole userRole
    ) throws Exception {
        List<UserDTO> employee = employeeService.findBranchEmployees(Id, userRole);
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("{employeeId}")
    public ResponseEntity<ApiResponse> resetPassword(@PathVariable Long employeeId,
                                                     @RequestParam(required = true) String password) throws Exception {
        employeeService.resetPassword(employeeId, password);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Password rested Successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
