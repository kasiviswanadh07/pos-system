package com.kasi.service.impl;

import com.kasi.configuration.JwtProvider;
import com.kasi.exception.UserException;
import com.kasi.model.User;
import com.kasi.repository.UserRepository;
import com.kasi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository ;
    private final JwtProvider jwtProvider ;

    @Override
    public User getUserFromJwtToken(String token) throws UserException {
        String email=jwtProvider.getEmailFromToken(token);
        User user=userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("Invalid Token");
        }
        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not Found");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user=userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not Found");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()-> new Exception("User Not Found"));
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
