package com.kasi.mapper;

import com.kasi.model.User;
import com.kasi.payload.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper {
    /*@Autowired
    static UserDTO userDTO;
*/
    public static UserDTO toDTO(User savedUser) {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setRole(savedUser.getRole());
        userDTO.setPhone(savedUser.getPhone());
        userDTO.setCreatedAt(savedUser.getCreatedAt());
        userDTO.setUpdatedAt(savedUser.getUpdatedAt());
        userDTO.setLastLogin(savedUser.getLastLogin());
        return userDTO;
    }

}
