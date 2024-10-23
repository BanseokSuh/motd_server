package com.banny.motd.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseStatus {

    private String code;
    private String desc;

    public ApiResponseStatus(ApiResponseStatusType statusType) {
        this.code = statusType.getCode();
        this.desc = statusType.getDesc();
    }

//    public static ApiResponseStatus of(ApiResponseStatusType statusType) {
//        return new ApiResponseStatus(statusType);
//    }

    public static ApiResponseStatus success() {
        return new ApiResponseStatus(ApiResponseStatusType.SUCCESS);
    }

    public static ApiResponseStatus error() {
        return new ApiResponseStatus(ApiResponseStatusType.FAIL_SERVER_ERROR);
    }

    public static ApiResponseStatus validateError() {
        return new ApiResponseStatus(ApiResponseStatusType.FAIL_VALIDATE_ERROR);
    }

}
