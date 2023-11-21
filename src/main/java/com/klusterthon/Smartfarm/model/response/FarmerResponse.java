package com.klusterthon.Smartfarm.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerResponse {
    private String fullName;
    private String phoneNo;
    private String location;
}
