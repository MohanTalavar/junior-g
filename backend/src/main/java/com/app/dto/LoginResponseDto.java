package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {
    private String userName;
    private String role;
    private String token;


    public LoginResponseDto(String userName, String role, String token) {
        this.userName = userName;
        this.role = role;
        this.token = token;

    }

}
