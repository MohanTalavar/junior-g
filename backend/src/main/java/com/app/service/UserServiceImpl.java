package com.app.service;

import com.app.dto.LoginResponseDto;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.pojos.User;
import com.app.repo.UserRepo;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public String addNewUserRecord(User newUser) {

        StringBuilder addRole = new StringBuilder("ROLE_");
        newUser.setRole(addRole.append(newUser.getRole()).toString());
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepo.save(newUser);
        return "New user " + newUser.getUserName() + " added.";
    }

    @Override
    public LoginResponseDto verifyUser(User user) {
        Authentication authentication;
        try {
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (authentication.isAuthenticated()) {
            return new LoginResponseDto(user.getUserName(), jwtService.generateToken(user.getUserName()));
        }

        throw new BadCredentialsException("Login failed!!!");
    }
}

