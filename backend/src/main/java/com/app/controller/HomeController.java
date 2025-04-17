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

	@GetMapping("/current-user")
	public String getCurrentUser(Principal principal){
		return principal.getName();
	}

}
