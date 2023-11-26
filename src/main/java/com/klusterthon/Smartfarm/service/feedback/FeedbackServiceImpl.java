package com.klusterthon.Smartfarm.service.feedback;

import com.klusterthon.Smartfarm.model.entity.FarmerFeedback;
import com.klusterthon.Smartfarm.model.repository.FeedbackRepository;
import com.klusterthon.Smartfarm.model.request.FeedbackRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.FeedbackResponse;
import com.klusterthon.Smartfarm.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{
    private final AuthenticationService authenticationService;
    private final FeedbackRepository feedbackRepository;
    @Override
    public ApiResponseDto<?> create(FeedbackRequest feedbackRequest) {
        FarmerFeedback feedback = mapToFeedback(feedbackRequest);
        FarmerFeedback savedFeedback = feedbackRepository.save(feedback);
        return new ApiResponseDto<>(
                "Successfully added farmer feedback",
                HttpStatus.CREATED.value(),
                mapToFeedbackResponse(savedFeedback)
        );
    }

    @Override
    public ApiResponseDto<?> getAll() {
        List<FeedbackResponse> feedbacks = feedbackRepository.findAll().stream()
                .map(this::mapToFeedbackResponse).toList();

        return new ApiResponseDto<>(
                "Successfully gotten all feedback",
                HttpStatus.OK.value(),
                feedbacks
        );
    }

    private FarmerFeedback mapToFeedback(FeedbackRequest feedbackRequest){
        FarmerFeedback farmerFeedback = new FarmerFeedback();
        farmerFeedback.setComment(feedbackRequest.getComment());
        farmerFeedback.setCreatedAt(LocalDateTime.now());
        farmerFeedback.setFarmer(authenticationService.getLoggedInFarmer());
        return farmerFeedback;
    }

    private FeedbackResponse mapToFeedbackResponse(FarmerFeedback farmerFeedback){
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setComment(farmerFeedback.getComment());
        feedbackResponse.setFarmerName(farmerFeedback.getFarmer().getFullName());
        feedbackResponse.setCreatedAt(farmerFeedback.getCreatedAt());
        return feedbackResponse;
    }
}
