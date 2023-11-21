package com.klusterthon.Smartfarm.service.impl;

import com.klusterthon.Smartfarm.exceptionHandler.ApplicationException;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
}
