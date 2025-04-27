package com.app.controller;

import com.app.dto.LoginResponseDto;
import com.app.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.app.dto.UserRequestResponseDto;
import com.app.pojos.User;
import com.app.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/add-new-user")
	public ResponseEntity<String> addNewUser(@Valid @RequestBody UserRequestResponseDto user){
		logger.info("New User creating: {}", user.getUserName());
		try{
			String response = userService.addNewUserRecord(new User(user));
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			logger.error("Error in creating new user: {}", user.getUserName() , e);
			return ResponseEntity.status(500).body("Error in creating user");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody UserRequestResponseDto user){
		logger.info("User log in requested: {}", user.getUserName());
		try {
			LoginResponseDto response = userService.verifyUser(new User(user));
//			emailService.sendEmail("tawareshubham89@gmail.com",
//					"Test Subject",
//					"This is the email body...");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error occurred during login for user {}: {}", user.getUserName(), e.getMessage(), e);
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("/get-csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request){
		return (CsrfToken) request.getAttribute("_csrf");
	}

}
