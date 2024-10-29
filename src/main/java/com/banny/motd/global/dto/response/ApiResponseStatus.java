package com.banny.motd.global.dto.response;

import com.banny.motd.global.exception.ApiResponseStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseStatus {

    private String code;
    private String desc;

    private ApiResponseStatus(ApiResponseStatusType statusType) {
        this.code = statusType.getCode();
        this.desc = statusType.getDesc();
    }

    public static ApiResponseStatus of(ApiResponseStatusType statusType) {
        return new ApiResponseStatus(statusType);
    }

    public static ApiResponseStatus success() {
        return ApiResponseStatus.of(ApiResponseStatusType.SUCCESS);
    }

    public static ApiResponseStatus serverError() {
        return ApiResponseStatus.of(ApiResponseStatusType.FAIL_SERVER_ERROR);
    }

    public static ApiResponseStatus validationError() {
        return ApiResponseStatus.of(ApiResponseStatusType.FAIL_VALIDATION_ERROR);
    }

}
