package com.kasi.payload.response;

import com.kasi.payload.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDTO user;

}
