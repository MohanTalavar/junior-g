package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.dto.LoginResponseDto;
import com.juniorg.dto.UserResponseDto;
import com.juniorg.pojos.User;
import com.juniorg.repo.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should add new user with prefixed role and encoded password")
    void addNewUserRecord_ShouldWork() {

        // Arrange
        User newUser = new User();
        newUser.setUserName("Mohan");
        newUser.setEmail("mohan@gmail.com");
        newUser.setPassword("mohan@123");
        newUser.setRole("ADMIN");

        // Act
        String result = userService.addNewUserRecord(newUser);

        // Assert
        verify(userRepo).save(newUser);

        assertTrue(newUser.getPassword().startsWith("$2a$")); // BCrypt Pattern
        assertEquals("ROLE_ADMIN", newUser.getRole());
        assertEquals("New user Mohan with role ROLE_ADMIN added!", result);

    }

    @Test
    @DisplayName("verifyUser should authenticate user and return LoginResponseDto")
    void verifyUser_ShouldReturnTokenAndRole() {
        // Arrange
        User input = new User();
        input.setUserName("mohan");
        input.setPassword("pass");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);

        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
        when(userRepo.findByUserName("mohan")).thenReturn(Optional.of(new User("mohan", "mohan@gmail.com", "pass", "ROLE_ADMIN")));
        when(jwtService.generateToken("mohan")).thenReturn("jwt-123");

        // Act
        LoginResponseDto response = userService.verifyUser(input);

        // Assert
        assertEquals("mohan", response.getUserName());
        assertEquals("ROLE_ADMIN", response.getRole());
        assertEquals("jwt-123", response.getToken());
    }

    @Test
    @DisplayName("verifyUser should throw BadCredentialsException on invalid credentials")
    void verifyUser_ShouldThrowOnBadCredentials() {
        User input = new User();
        input.setUserName("mohan");
        input.setPassword("bad");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad"));

        assertThrows(BadCredentialsException.class, () -> {
            userService.verifyUser(input);
        });
    }


    @Test
    @DisplayName("verifyUser should throw Login failed when not authenticated")
    void verifyUser_NotAuthenticated_ShouldThrow() {
        User input = new User();
        input.setUserName("mohan");
        input.setPassword("pass");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(false);

        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);

        assertThrows(BadCredentialsException.class, () -> {
            userService.verifyUser(input);
        });
    }

    @Test
    @DisplayName("Should send password reset email when user and email match")
    void initiatePasswordReset_ShouldSendEmail() {
        User user = new User();
        user.setUserName("mohan");
        user.setEmail("mohan@gmail.com");

        when(userRepo.findByUserName("mohan")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("mohan")).thenReturn("reset-token");

        String result = userService.initiatePasswordReset("mohan", "mohan@gmail.com");

        verify(emailService).sendEmail(eq("mohan@gmail.com"), contains("Password Reset"), contains("https"));
        assertTrue(result.contains("Password reset email sent"));
    }

    @Test
    @DisplayName("Should throw when username/email do not match")
    void initiatePasswordReset_InvalidEmail_ShouldThrow() {
        when(userRepo.findByUserName("mohan")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            userService.initiatePasswordReset("mohan", "wrong@gmail.com");
        });
    }

    @Test
    @DisplayName("Should reset password successfully when token is valid")
    void shouldResetPasswordSuccessfully() {
        String token = "valid-token";
        String username = "john";
        String newPassword = "newpass";

        when(jwtService.extractUserName(token)).thenReturn(username);
        when(userRepo.findByUserName(username)).thenReturn(Optional.of(new User()));

        String result = userService.resetPassword(token, newPassword);

        assertEquals("Password has been reset successfully!", result);
        verify(userRepo).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when token is invalid")
    void shouldThrowExceptionOnInvalidToken() {
        when(jwtService.extractUserName(anyString())).thenThrow(new RuntimeException());

        assertThrows(IllegalArgumentException.class, () ->
                userService.resetPassword("bad-token", "pass123"));
    }

    @Test
    @DisplayName("Should return success even when user does not exist (idempotent)")
    void shouldReturnSuccessWhenUserNotFound() {
        when(jwtService.extractUserName("token")).thenReturn("ghost");
        when(userRepo.findByUserName("ghost")).thenReturn(Optional.empty());

        String result = userService.resetPassword("token", "pass");
        assertEquals("Password has been reset successfully!", result);
    }

    @Test
    @DisplayName("Should return sorted user list")
    void shouldReturnSortedUserList() {
        List<User> users = List.of(
                new User("zebra"),
                new User("alpha")
        );

        when(userRepo.findAll()).thenReturn(users);

        List<UserResponseDto> result = userService.fetchUserList();

        assertEquals("alpha", result.get(0).getUserName());
    }

    @Test
    @DisplayName("Should delete user when exists")
    void shouldDeleteUserWhenExists() {
        User user = new User();
        user.setId(1L);

        when(userRepo.findByUserName("john")).thenReturn(Optional.of(user));

        String result = userService.deleteUser("john");

        verify(userRepo).delete(user);
        assertEquals("User john deleted successfully!", result);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing user")
    void shouldThrowOnNonExistingDelete() {
        when(userRepo.findByUserName("ghost")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.deleteUser("ghost"));
    }

    @Test
    @DisplayName("Should update user details successfully")
    void shouldUpdateUserDetails() {
        User existing = new User("john");
        when(userRepo.findByUserName("john")).thenReturn(Optional.of(existing));

        UserResponseDto updated = new UserResponseDto("johnny", "mail@mail.com", "ADMIN");

        UserResponseDto result = userService.updateUserRecord("john", updated);

        assertEquals("ROLE_ADMIN", result.getRole());
    }

    @Test
    @DisplayName("Should fetch user details successfully")
    void shouldFetchUserDetails() {
        User user = new User("john");
        when(userRepo.findByUserName("john")).thenReturn(Optional.of(user));

        UserResponseDto result = userService.fetchUserDetailsByUserName("john");

        assertEquals("john", result.getUserName());
    }

}
