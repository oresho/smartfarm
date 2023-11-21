package com.klusterthon.Smartfarm.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "phoneNo is required")
    private String phoneNo;
    @NotBlank(message = "password is required")
    private String password;
}
