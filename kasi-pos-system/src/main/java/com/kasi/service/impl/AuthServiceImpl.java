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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomerUserImplementation customerUserImplementation;
    //    private User newUser;




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
        User savedUser = userRepository.save(newUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse =new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setUser(UserMapper.toDTO(savedUser));
        return authResponse;
    }

    @Override
    public AuthResponse login(UserDTO userDTO) throws UserException {
        Authentication authentication = authenticate(userDTO.getEmail(), userDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);
        User user = userRepository.findByEmail(userDTO.getEmail());
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        AuthResponse authResponse =new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully");
        authResponse.setUser(UserMapper.toDTO(user));
        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails=customerUserImplementation.loadUserByUsername(email);
        if(userDetails == null){
            throw  new UserException("Email Id doesn't  exist "+email);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw  new UserException("Password doesn't match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
