package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ApiResponseStatus;
import com.banny.motd.global.dto.response.ApiResponseStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private ApiResponseStatus status;
    private ErrorResult result;

    public ApplicationException(ApiResponseStatusType statusType, String message) {
        this.status = new ApiResponseStatus(statusType);
        this.result = new ErrorResult(message);
    }

    public ApplicationException(ApiResponseStatusType statusType) {
        this.status = new ApiResponseStatus(statusType);
        this.result = new ErrorResult();
    }

}
