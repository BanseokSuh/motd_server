package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ApiResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private ApiResponseStatus status;
    private ErrorResult result;

    public ApplicationException(ApiResponseStatusType statusType, String message) {
        this.status = ApiResponseStatus.of(statusType);
        this.result = ErrorResult.of(message);
    }

    public ApplicationException(ApiResponseStatusType statusType) {
        this.status = ApiResponseStatus.of(statusType);
        this.result = ErrorResult.of();
    }

}
