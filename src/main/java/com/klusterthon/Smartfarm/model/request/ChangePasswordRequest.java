package com.klusterthon.Smartfarm.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "newPassword is required")
    private String newPassword;
    @NotBlank(message = "farmerEmail is required")
    public String farmerEmail;
}
