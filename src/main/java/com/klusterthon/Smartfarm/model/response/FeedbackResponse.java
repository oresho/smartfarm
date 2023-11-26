package com.klusterthon.Smartfarm.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private String comment;
    private String farmerName;
    private LocalDateTime createdAt;
}
