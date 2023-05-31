package com.example.baemin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserRequest {

    @NotBlank(message = "email: {NotBlank}")
    private String email;

    @NotBlank(message = "password: {NotBlank}")
    private String password;

}
