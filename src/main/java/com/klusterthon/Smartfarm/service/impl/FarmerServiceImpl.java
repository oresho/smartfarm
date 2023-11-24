package com.klusterthon.Smartfarm.service.impl;

import com.klusterthon.Smartfarm.exceptionHandler.ApplicationException;
import com.klusterthon.Smartfarm.model.entity.Farmer;
import com.klusterthon.Smartfarm.model.repository.FarmerRepository;
import com.klusterthon.Smartfarm.model.request.FarmerRegistrar;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.FarmerResponse;
import com.klusterthon.Smartfarm.service.FarmerService;
import com.klusterthon.Smartfarm.service.email.EmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {
    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    @Value("${server.mail.username}")
    private String mailUsername;
    @Override
    public ApiResponseDto<?> create(FarmerRegistrar farmerRegistrar) {
        if (farmerRepository.findByPhoneNo(farmerRegistrar.getPhoneNo()).isPresent()){
            throw new ApplicationException("This phone number is already used");
        }
        Farmer savedFarmer = farmerRepository.save(mapToFarmer(farmerRegistrar));
        FarmerResponse farmerResponse = mapToFarmerResponse(savedFarmer);
//        publisher.publishEvent(new EmailEvent(farmerRegistrar.getPhoneNo(),"Sign up",mailUsername, "test"));
        return new ApiResponseDto<>(
                "Successfully Signed up farmer",
                HttpStatus.CREATED.value(), farmerResponse
        );
    }

    private FarmerResponse mapToFarmerResponse(Farmer savedFarmer) {
        FarmerResponse farmerResponse = new FarmerResponse();
        farmerResponse.setFullName(savedFarmer.getFullName());
        farmerResponse.setPhoneNo(savedFarmer.getPhoneNo());
        farmerResponse.setLocation(savedFarmer.getLocation());
        return farmerResponse;
    }

    private Farmer mapToFarmer(FarmerRegistrar farmerRegistrar){
        Farmer farmer = new Farmer();
        farmer.setFullName(farmerRegistrar.getFirstname() + " " + farmerRegistrar.getLastname());
        farmer.setPhoneNo(farmerRegistrar.getPhoneNo());
        farmer.setLocation(farmerRegistrar.getLocation());
        farmer.setPasswordHash(passwordEncoder.encode(farmerRegistrar.getPassword()));
        return farmer;
    }
}
