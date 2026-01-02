package com.kasi.mapper;

import com.kasi.model.User;
import com.kasi.payload.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User savedUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setFullname(savedUser.getFullname());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setRole(savedUser.getRole());
        userDTO.setPhone(savedUser.getPhone());
        userDTO.setCreatedAt(savedUser.getCreatedAt());
        userDTO.setUpdatedAt(savedUser.getUpdatedAt());
        userDTO.setLastLogin(savedUser.getLastLogin());
        userDTO.setBranchId(savedUser.getBranch() != null ? savedUser.getBranch().getId() : null);
        userDTO.setStoreId(savedUser.getStore() != null ? savedUser.getStore().getId() : null);
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User createdUser = new User();
        createdUser.setEmail(userDTO.getEmail());
        createdUser.setRole(userDTO.getRole());
        createdUser.setFullname(userDTO.getFullname());
        createdUser.setCreatedAt(userDTO.getCreatedAt());
        createdUser.setUpdatedAt(userDTO.getUpdatedAt());
        createdUser.setLastLogin(userDTO.getLastLogin());
        createdUser.setPhone(userDTO.getPhone());
        createdUser.setPassword(userDTO.getPassword());
        return createdUser;
    }
}
