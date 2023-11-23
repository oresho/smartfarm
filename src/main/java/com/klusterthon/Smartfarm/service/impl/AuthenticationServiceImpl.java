package com.klusterthon.Smartfarm.service.impl;

import com.klusterthon.Smartfarm.exceptionHandler.ApplicationException;
import com.klusterthon.Smartfarm.exceptionHandler.ResourceNotFoundException;
import com.klusterthon.Smartfarm.model.entity.Farmer;
import com.klusterthon.Smartfarm.model.repository.FarmerRepository;
import com.klusterthon.Smartfarm.model.request.FarmerRegistrar;
import com.klusterthon.Smartfarm.model.request.LoginRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.AuthenticationResponse;
import com.klusterthon.Smartfarm.security.JwtService;
import com.klusterthon.Smartfarm.service.AuthenticationService;
import com.klusterthon.Smartfarm.service.FarmerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final FarmerService farmerService;
    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ApiResponseDto<?> signUp(FarmerRegistrar farmerRegistrar) {
        return farmerService.create(farmerRegistrar);
    }

    @Override
    public ApiResponseDto<?> login(LoginRequest loginRequest) {
        Farmer farmer = farmerRepository.findByPhoneNo(loginRequest.getPhoneNo())
                .orElseThrow(()-> new ApplicationException("User is not registered"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),farmer.getPasswordHash())){
            throw  new ApplicationException("Invalid Credentials");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(farmer.getPhoneNo(),
                farmer.getPasswordHash());
        User user = new User(farmer.getPhoneNo(), farmer.getPasswordHash(), List.of(new SimpleGrantedAuthority("USER")));
        String accessToken = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(farmer.getFullName(), accessToken);
        return new ApiResponseDto<>(
                "Successfully logged in User",
                HttpStatus.OK.value(),
                authenticationResponse
        );
    }

//    @Override
//    public ApiResponseDto<?> forgotPassword(String phoneNo) {
//        log.info("Entered here===="+ phoneNo);
//        Farmer farmer = farmerRepository.findByPhoneNo(phoneNo)
//                .orElseThrow(()-> new ResourceNotFoundException("User with this phone number does not exist"));
//        String token = RandomStringUtils.randomNumeric(6);
//        log.info("token here====" + token);
//        log.info(System.getenv("TWILIO_ACCOUNT_SID")+" "+ System.getenv("TWILIO_AUTH_TOKEN"));
//
//
//        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
//        return new ApiResponseDto<>(
//                "Successfully sent reset code to: " + phoneNo,
//                HttpStatus.OK.value(),
//        );
//    }
}
