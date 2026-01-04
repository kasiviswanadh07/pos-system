package com.kasi.payload.dto;

import com.kasi.domain.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private UserRole role;
    @NotBlank(message = "Password can't be empty")
    private String password;
    private Long branchId;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

}
