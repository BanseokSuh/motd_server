package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ApiStatus;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private ApiStatus status;
    private ApiExceptionResult result;

    public ApplicationException(ApiStatusType statusType, String message) {
        this.status = ApiStatus.of(statusType);
        this.result = ApiExceptionResult.of(message);
    }

    public ApplicationException(ApiStatusType statusType) {
        this.status = ApiStatus.of(statusType);
        this.result = ApiExceptionResult.of();
    }

}
