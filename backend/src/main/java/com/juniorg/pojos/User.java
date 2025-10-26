package com.juniorg.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.juniorg.dto.UserRequestResponseDto;

import lombok.*;

@Entity
@Table(name = "users_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	public User(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
