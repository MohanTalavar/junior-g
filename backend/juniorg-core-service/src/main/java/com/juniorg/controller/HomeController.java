package com.juniorg.controller;

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
