package com.klusterthon.Smartfarm.service.farmer;

import com.klusterthon.Smartfarm.model.request.FarmerRegistrar;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.FarmerResponse;

public interface FarmerService {
    ApiResponseDto<?> create(FarmerRegistrar farmerRegistrar);
}
