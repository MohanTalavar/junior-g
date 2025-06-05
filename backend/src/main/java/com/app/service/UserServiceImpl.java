package com.app.service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dto.LoginResponseDto;
import com.app.dto.UserResponseDto;
import com.app.pojos.User;
import com.app.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private EmailService emailService;

    @Override
    public String addNewUserRecord(User newUser) {

        StringBuilder addRole = new StringBuilder("ROLE_");
        newUser.setRole(addRole.append(newUser.getRole()).toString());
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepo.save(newUser);
        return "New user " + newUser.getUserName() + " with role " + newUser.getRole() + " added!";
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
            User authenticatedUser = retrieveUserDetails(user.getUserName());
            return new LoginResponseDto(user.getUserName(), authenticatedUser.getRole(), jwtService.generateToken(user.getUserName()));
        }

        throw new BadCredentialsException("Login failed!!!");
    }

    @Override
    public User retrieveUserDetails(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Override
    public String initiatePasswordReset(String userName, String email) {
        User user = userRepo.findByUserName(userName);
        if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
            throw new IllegalArgumentException("Invalid username or email");
        }

        String token = jwtService.generateToken(userName); // can use a shorter TTL if needed

        String resetLink = "http://localhost:5173/reset-password?token=" + token;
        String body = "Hi " + userName + ",\n\nClick the link below to reset your password:\n" + resetLink;

        emailService.sendEmail(email, "Password Reset Request", body);
        return "Password reset email sent to " + email;
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        String username;
        try {
            username = jwtService.extractUserName(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        User user = userRepo.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);

        return "Password has been reset successfully!";
    }

    @Override
    public List<UserResponseDto> fetchUserList() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .sorted(Comparator.comparing(User::getUserName))
                .map(UserResponseDto::new)
                .toList();
    }

    @Override
    public String deleteUser(String userName) {
        User persistentUser = userRepo.findByUserName(userName);

        if (persistentUser != null) {
            userRepo.deleteById(persistentUser.getId());
        }

        return "User " + userName + " deleted successfully!";
    }

    @Override
    public UserResponseDto updateUserRecord(String userName, UserResponseDto updatedUser) {

        User persistentUser = userRepo.findByUserName(userName);

        persistentUser.setUserName(updatedUser.getUserName());
        persistentUser.setEmail(updatedUser.getEmail());
        persistentUser.setRole(updatedUser.getRole());

        return new UserResponseDto(persistentUser);
    }

    @Override
    public UserResponseDto fetchUserDetailsByUserName(String userName) {

        User persistentUser = userRepo.findByUserName(userName);

        if (persistentUser == null) throw new ResourceNotFoundException("User " + userName + " NOT found!!!");

        return new UserResponseDto(persistentUser);
    }

}

