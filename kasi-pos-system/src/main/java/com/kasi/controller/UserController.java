package com.kasi.controller;

import com.kasi.exception.UserException;
import com.kasi.mapper.UserMapper;
import com.kasi.model.User;
import com.kasi.payload.dto.UserDTO;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private  final UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws  UserException, Exception {
        User user=userService.getUserFromJwtToken(jwt);
        if(user == null){
            throw new Exception("User not Found");
        }
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@RequestHeader("Authorization") String jwt,
                                               @PathVariable Long id) throws UserException, Exception {
        User user=userService.getUserById(id);

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
