package com.klusterthon.Smartfarm.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmerRegistrar {
    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotBlank(message = "lastname is required")
    private String lastname;
    @NotBlank(message = "phoneNo is required")
    private String phoneNo;
    @NotBlank(message = "location is required")
    private String location;
    @NotBlank(message = "password is required")
    private String password;
}
