package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/public") // adding this for the public so that no authentication is needed.
						   // refer MySpringSecurityConfig.java
public class HomeController {
	
	@GetMapping("/home")
	public ResponseEntity<String> home(){
		String response = "Welcome to Junior G Pre School";
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> login(){
		String response = "Login to Junior G Pre School";
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/register")
	public ResponseEntity<String> register(){
		String response = "Register to Junior G Pre School";
		return ResponseEntity.ok(response);
	}

	@GetMapping("/current-user")
	public String getCurrentUser(Principal principal){
		return principal.getName();
	}

}
