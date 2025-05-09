package com.app.controller;

import com.app.dto.EnquiryDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
