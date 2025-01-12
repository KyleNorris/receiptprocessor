package com.kylenorris.rewards.recieptprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private int status;
    private String message;
    private List<ValidationError> errors;
}
