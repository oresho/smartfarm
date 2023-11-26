package com.klusterthon.Smartfarm.service.feedback;

import com.klusterthon.Smartfarm.model.request.FeedbackRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;

public interface FeedbackService {
    ApiResponseDto<?> create(FeedbackRequest feedbackRequest);
    ApiResponseDto<?> getAll();
}
