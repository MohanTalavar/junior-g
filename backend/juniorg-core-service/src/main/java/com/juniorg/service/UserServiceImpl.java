package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.dto.LoginResponseDto;
import com.juniorg.dto.UserResponseDto;
import com.juniorg.pojos.User;
import com.juniorg.repo.UserRepo;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final UserRepo userRepo;
    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String addNewUserRecord(User newUser) {

        log.info("Adding new user {} with initial role: {}", newUser.getUserName(), newUser.getRole());

        newUser.setRole("ROLE_" + newUser.getRole());
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepo.save(newUser);

        log.info("Assigned prefixed role {} to user {}", newUser.getRole(), newUser.getUserName());

        return "New user " + newUser.getUserName() + " with role " + newUser.getRole() + " added!";
    }

    @Override
    public LoginResponseDto verifyUser(User user) {

        log.info("Attempting to authenticate user {}", user.getUserName());

        Authentication authentication;
        try {
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        } catch (AuthenticationException ex) {

            log.warn("Authentication failed for username {}", user.getUserName());
            throw new BadCredentialsException("Invalid username or password");
        }
        if (authentication.isAuthenticated()) {

            User authenticatedUser = retrieveUserDetails(user.getUserName());
            log.info("User {} authenticated successfully", user.getUserName());
            return new LoginResponseDto(
                    user.getUserName(),
                    authenticatedUser.getRole(),
                    jwtService.generateToken(user.getUserName()));
        }

        log.warn("Authentication returned false for {}", user.getUserName());
        throw new BadCredentialsException("Login failed!!!");
    }

    // Removing from the IUser Service
    // Making this method private
    private User retrieveUserDetails(String userName) {
        return userRepo.findByUserName(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found: "+ userName));
    }

    @Override
    public String initiatePasswordReset(String userName, String email) {

        log.info("Password reset requested for user {} with email {}", userName, email);

        User user = userRepo.findByUserName(userName)
                .orElseThrow(()-> new IllegalArgumentException("Invalid username or email"));

        if (!user.getEmail().equalsIgnoreCase(email)) {
            log.warn("Password reset failed: email mismatch for user {}", userName);
            throw new IllegalArgumentException("Invalid username or email");
        }

        String token = jwtService.generateToken(userName); // can use a shorter TTL if needed

        // Local to Remote changes
        // #LTR
        String remoteResetLink = "https://juniorg.site/reset-password?token=" + token;
        //String localResetLink = "http://localhost:5173/reset-password?token=" + token;

        String body = "Hi " + userName + ",\n\nClick the link below to reset your password:\n" + remoteResetLink;

        emailService.sendEmail(email, "Password Reset Request", body);
        log.info("Password reset email sent to {}", email);

        return "Password reset email sent to " + email;
    }

    @Override
    public String resetPassword(String token, String newPassword) {

        log.info("Password reset attempt with token");

        String username;
        try {
            username = jwtService.extractUserName(token);
        } catch (ExpiredJwtException e) {
            log.warn("Expired token");
            throw new IllegalArgumentException("Token expired");
        } catch (Exception e) {
            log.warn("Invalid token");
            throw new IllegalArgumentException("Invalid token");
        }

        Optional<User> optionalUser = userRepo.findByUserName(username);
        if (optionalUser.isEmpty()) {
            log.warn("Attempt to reset password for non-existing user {}", username);
            // do not reveal existence
            return "Password has been reset successfully!"; // idempotent
        }

        User user = optionalUser.get();
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);

        log.info("Password reset successful for {}", username);
        return "Password has been reset successfully!";
    }

    @Override
    public List<UserResponseDto> fetchUserList() {

        log.info("Fetching user list");

        List<User> users = userRepo.findAll();
        return users.stream()
                .sorted(Comparator.comparing(User::getUserName))
                .map(UserResponseDto::new)
                .toList();
    }

    @Override
    public String deleteUser(String userName) {

        log.info("Delete user request {}", userName);

        User persistentUser = userRepo.findByUserName(userName)
                .orElseThrow(()->new ResourceNotFoundException("User not found: "+userName));

        userRepo.delete(persistentUser);
        log.info("User {} deleted successfully!", userName);

        return "User " + userName + " deleted successfully!";
    }

    @Override
    public UserResponseDto updateUserRecord(String userName, UserResponseDto updatedUser) {

        log.info("Updating user {}", userName);

        User persistentUser = userRepo.findByUserName(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found: "+userName));

        // Dirty checking saves the persistent user
        persistentUser.setUserName(updatedUser.getUserName());
        persistentUser.setEmail(updatedUser.getEmail());
        persistentUser.setRole("ROLE_" + updatedUser.getRole());

        log.info("User {} updated successfully", userName);

        return new UserResponseDto(persistentUser);
    }

    @Override
    public UserResponseDto fetchUserDetailsByUserName(String userName) {

        log.info("Fetching details for {}", userName);

        User persistentUser = userRepo.findByUserName(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found: "+ userName));

        return new UserResponseDto(persistentUser);
    }

}

