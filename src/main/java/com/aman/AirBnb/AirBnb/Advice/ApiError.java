package com.aman.AirBnb.AirBnb.Advice;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> subErrors;

}
