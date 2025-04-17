package com.app.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestResponseDto {

	@NotBlank(message = "User name cannot be blank")
	private String userName;
	
	//@NotBlank(message = "Email cannot be blank")
	private String email;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
	//@NotBlank(message = "Role cannot be blank")
	private String role;
}
