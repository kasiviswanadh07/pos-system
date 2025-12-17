package com.kasi.service.impl;
import com.kasi.configuration.JwtProvider;
import com.kasi.domain.UserRole;
import com.kasi.exception.UserException;
import com.kasi.mapper.UserMapper;
import com.kasi.model.User;
import com.kasi.payload.dto.UserDTO;
import com.kasi.payload.response.AuthResponse;
import com.kasi.repository.UserRepository;
import com.kasi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private CustomerUserImplementation customerUserImplementation;
//    private User newUser;
    private AuthResponse authResponse;


    @Override
    public AuthResponse singUp(UserDTO userDTO) throws UserException {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new UserException("Email id already registered...........!");
        }
        if (userDTO.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException("Role admin is not allowed ");
        }
        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole(userDTO.getRole());
        newUser.setFullname(userDTO.getFullname());
        newUser.setPhone(userDTO.getPhone());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
       User savedUser= userRepository.save(newUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setUser(UserMapper.toDTO(savedUser));
        return authResponse;
    }

    @Override
    public AuthResponse login(UserDTO userDTO) {
        return null;
    }
}
