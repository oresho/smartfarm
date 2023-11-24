package com.klusterthon.Smartfarm.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "token is required")
    private String token;
    @NotBlank(message = "farmerEmail is required")
    private String farmerEmail;
}
