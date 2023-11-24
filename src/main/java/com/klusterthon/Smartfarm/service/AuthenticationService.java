package com.klusterthon.Smartfarm.service;

import com.klusterthon.Smartfarm.model.entity.Farmer;
import com.klusterthon.Smartfarm.model.request.FarmerRegistrar;
import com.klusterthon.Smartfarm.model.request.LoginRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;

public interface AuthenticationService {
    ApiResponseDto<?> signUp(FarmerRegistrar farmerRegistrar);
    ApiResponseDto<?> login(LoginRequest loginRequest);
    Farmer getLoggedInFarmer();
//    ApiResponseDto<?> forgotPassword(String phoneNo);
}
