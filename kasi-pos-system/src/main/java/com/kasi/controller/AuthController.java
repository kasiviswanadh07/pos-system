package com.kasi.controller;

import com.kasi.exception.UserException;
import com.kasi.payload.dto.UserDTO;
import com.kasi.payload.response.AuthResponse;
import com.kasi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> sinupHandler(@RequestBody UserDTO userDTO) throws UserException {
        return ResponseEntity.ok(authService.singUp(userDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody UserDTO userDTO) throws UserException {
        return ResponseEntity.ok(authService.login(userDTO));
    }
}
