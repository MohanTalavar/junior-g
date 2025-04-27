package com.app.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.app.dto.UserRequestResponseDto;

import lombok.*;

@Entity
@Table(name = "users_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseEntity {
	
	@Column(length = 30)
	private String userName;

	@Column(length = 50, unique = true)
	private String email;

	@Column(length = 100)
	private String password;

	@Column(length = 20)
	private String role;
	
	public User(UserRequestResponseDto user) {
		this.userName= user.getUserName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.role = user.getRole();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
