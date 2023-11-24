package com.klusterthon.Smartfarm.service.auth;

import com.klusterthon.Smartfarm.exceptionHandler.ApplicationException;
import com.klusterthon.Smartfarm.exceptionHandler.ResourceNotFoundException;
import com.klusterthon.Smartfarm.model.entity.Farmer;
import com.klusterthon.Smartfarm.model.entity.OTP;
import com.klusterthon.Smartfarm.model.repository.FarmerRepository;
import com.klusterthon.Smartfarm.model.repository.OTPRepository;
import com.klusterthon.Smartfarm.model.request.FarmerRegistrar;
import com.klusterthon.Smartfarm.model.request.LoginRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.AuthenticationResponse;
import com.klusterthon.Smartfarm.model.response.FarmerResponse;
import com.klusterthon.Smartfarm.security.JwtService;
import com.klusterthon.Smartfarm.service.email.EmailEvent;
import com.klusterthon.Smartfarm.service.farmer.FarmerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final FarmerService farmerService;
    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApplicationEventPublisher publisher;
    private final OTPRepository otpRepository;
    @Value("${server.mail.username}")
    private String mailUsername;

    @Override
    public ApiResponseDto<?> signUp(FarmerRegistrar farmerRegistrar) {
        return farmerService.create(farmerRegistrar);
    }

    @Override
    public ApiResponseDto<?> login(LoginRequest loginRequest) {
        Farmer farmer = farmerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new ApplicationException("User is not registered"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),farmer.getPasswordHash())){
            throw  new ApplicationException("Invalid Credentials");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(farmer.getEmail(),
                farmer.getPasswordHash());
        User user = new User(farmer.getEmail(), farmer.getPasswordHash(), List.of(new SimpleGrantedAuthority("USER")));
        String accessToken = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(farmer.getFullName(), accessToken);
        return new ApiResponseDto<>(
                "Successfully logged in User",
                HttpStatus.OK.value(),
                authenticationResponse
        );
    }

    @Override
    public Farmer getLoggedInFarmer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return farmerRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Farmer not logged in"));
    }

    @Override
    public ApiResponseDto<?> forgotPassword(String email) {
        Farmer farmer = farmerRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User with this email does not exist"));
        String token = RandomStringUtils.randomNumeric(4);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
        String subject = "Password Reset";
        String from = mailUsername;
        String htmlContent =
                "<p>Please Use the OTP below to reset your password</p> " +
                        "<p>Date: " + LocalDate.now()+"</p>" +
                        "<p>Time: " + LocalTime.now()+"</p>" +
                        "\n" +
                        "<p>This OTP Lasts for 10 minutes: </p>" +
                        "<p style='font-size:30px'>" +token+"</p>";
        OTP otp = new OTP();
        otp.setToken(token);
        otp.setFarmerEmail(email);
        otp.setExpirationTime(expirationTime);
        otpRepository.save(otp);
        publisher.publishEvent(new EmailEvent(email,subject,from, htmlContent));
        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
        return new ApiResponseDto<>(
                "Successfully sent reset code to: " + email,
                HttpStatus.OK.value(),
                "OTP expires in 10 minutes"
        );
    }

    @Override
    public ApiResponseDto<?> resetPassword(String token, String farmerEmail) {
        OTP otp = otpRepository.findByTokenAndFarmerEmail(token, farmerEmail)
                .orElseThrow(() -> new ApplicationException("Invalid OTP"));
        if (otp.getExpirationTime().isAfter(LocalDateTime.now())) {
            Farmer farmer = farmerRepository.findByEmail(farmerEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User with this email does not exist"));
            farmer.setCanResetPassword(true);
            farmerRepository.save(farmer);
        }
        else {
            throw new ApplicationException("OTP expired please retry");
        }
        return new ApiResponseDto<>(
                "Successful reset password request",
                HttpStatus.OK.value(),
                "Farmer can now change password"
        );
    }

    @Override
    public ApiResponseDto<?> changePassword(String newPassword, String farmerEmail) {
        Farmer farmer = farmerRepository.findByEmail(farmerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User with this email does not exist"));
        if (farmer.isCanResetPassword()) {
            farmer.setPasswordHash(passwordEncoder.encode(newPassword));
            farmer.setCanResetPassword(false);
            farmerRepository.save(farmer);
        }
        else {
            throw new ApplicationException("This User is not permitted to reset Password");
        }
        return new ApiResponseDto<>(
                "Successfully changed password",
                HttpStatus.OK.value(),
                ""
        );
    }

    @Override
    public ApiResponseDto<?> getUserProfile() {
        return  new ApiResponseDto<>(
                "Successfully gotten User",
                HttpStatus.OK.value(),
                mapToFarmerResponse(getLoggedInFarmer())
        );
    }


    private FarmerResponse mapToFarmerResponse(Farmer savedFarmer) {
        FarmerResponse farmerResponse = new FarmerResponse();
        farmerResponse.setFullName(savedFarmer.getFullName());
        farmerResponse.setEmail(savedFarmer.getEmail());
        farmerResponse.setPhoneNo(savedFarmer.getPhoneNo());
        farmerResponse.setLocation(savedFarmer.getLocation());
        return farmerResponse;
    }
}
