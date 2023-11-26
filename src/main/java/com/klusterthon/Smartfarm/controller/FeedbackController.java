package com.klusterthon.Smartfarm.controller;

import com.klusterthon.Smartfarm.model.request.FeedbackRequest;
import com.klusterthon.Smartfarm.model.request.PlantRequest;
import com.klusterthon.Smartfarm.service.feedback.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "Get all feedback from farmers")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllFeedback(){
        return new ResponseEntity<>(feedbackService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Create feedback comment for logged in farmer")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> addNewFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest){
        return new ResponseEntity<>(feedbackService.create(feedbackRequest), HttpStatus.CREATED);
    }
}
