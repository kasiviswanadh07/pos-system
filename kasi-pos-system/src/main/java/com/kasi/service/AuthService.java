package com.kasi.service;

import com.kasi.exception.UserException;
import com.kasi.payload.dto.UserDTO;
import com.kasi.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse singUp(UserDTO userDTO) throws UserException;
    AuthResponse login(UserDTO userDTO) throws UserException;
}
