package com.demo.apiWebResponse;

import com.demo.apiWebResponse.domain.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.demo.apiWebResponse.domain.enums.StatusEnum;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
        @ExceptionHandler(DefaultException.class)
        public ResponseEntity<ApiResponse> handleException(DefaultException exception) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            StatusEnum.BAD_REQUEST,
                            "An error occurred: " + exception.getMessage(),
                            null
                    ), HttpStatus.BAD_REQUEST);
        }
}
