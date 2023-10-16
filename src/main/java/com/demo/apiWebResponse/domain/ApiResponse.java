package com.demo.apiWebResponse.domain;

import com.demo.apiWebResponse.domain.enums.StatusEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApiResponse {
    public ApiResponse() {
        this.status = StatusEnum.BAD_REQUEST;
        this.message = "UNDEFINED ERROR";
        this.data = null;
    }
    public ApiResponse(StatusEnum statusEnum, String message, Object data) {
        this.status = statusEnum;
        this.message = message;
        this.data = data;
    }
    private StatusEnum status;
    private String message;
    private Object data;
}
