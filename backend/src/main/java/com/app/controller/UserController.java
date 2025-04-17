package com.app.controller;

import com.app.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/add-new-user")
	public ResponseEntity<String> addNewUser(@Valid @RequestBody UserRequestResponseDto user){
		
		String response = userService.addNewUserRecord(new User(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody UserRequestResponseDto user){
		LoginResponseDto response = userService.verifyUser(new User(user));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get-csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request){
		return (CsrfToken) request.getAttribute("_csrf");
	}

}
