package com.app.controller;

import com.app.dto.*;
import com.app.pojos.User;
import com.app.service.EmailService;
import com.app.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/{userName}")
    public ResponseEntity<UserResponseDto> findUserByUserName(@PathVariable String userName){

        log.info("Fetching the user details for {}", userName);
        UserResponseDto user = userService.fetchUserDetailsByUserName(userName);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add-new-user")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody UserRequestResponseDto user) {

        String response = userService.addNewUserRecord(new User(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserRequestResponseDto user) {
        log.info("User log in requested: {}", user.getUserName());
        LoginResponseDto response = userService.verifyUser(new User(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDto req) {
        String result = userService.initiatePasswordReset(req.getUserName(), req.getEmail());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto request) {
        String response = userService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDto>> lisOfUsers() {
        List<UserResponseDto> users = userService.fetchUserList();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> deleteUserByUserName(@PathVariable String userName) {

        String resp = userService.deleteUser(userName);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String userName, @RequestBody UserResponseDto updatedUser) {
        UserResponseDto user = userService.updateUserRecord(userName,updatedUser);
        return ResponseEntity.ok(user);
    }



    @GetMapping("/get-csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
