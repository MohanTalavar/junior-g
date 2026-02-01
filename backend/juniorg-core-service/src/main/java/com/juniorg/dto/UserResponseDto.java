package com.juniorg.dto;

import com.juniorg.pojos.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @NotBlank(message = "User name cannot be blank")
    private String userName;

    //@NotBlank(message = "Email cannot be blank")
    private String email;

    //@NotBlank(message = "Role cannot be blank")
    private String role;

    public UserResponseDto(User user){
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
