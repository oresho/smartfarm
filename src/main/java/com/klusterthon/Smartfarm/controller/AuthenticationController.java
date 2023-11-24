package com.klusterthon.Smartfarm.controller;

import com.klusterthon.Smartfarm.model.request.FarmerRegistrar;
import com.klusterthon.Smartfarm.model.request.LoginRequest;
import com.klusterthon.Smartfarm.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "User attempts to signup")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody FarmerRegistrar farmerRegistrar){
        return new ResponseEntity<>(authenticationService.signUp(farmerRegistrar), HttpStatus.CREATED);
    }

    @Operation(summary = "User attempts to login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }

//    @GetMapping("/forgot-password")
//    private ResponseEntity<?> forgotPassword(@RequestParam String phoneNo){
//        return new ResponseEntity<>(authenticationService.forgotPassword(phoneNo), HttpStatus.OK);
//    }
}
