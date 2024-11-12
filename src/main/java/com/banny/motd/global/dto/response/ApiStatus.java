package com.banny.motd.global.dto.response;

import com.banny.motd.global.exception.ApiStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiStatus {

    private String code;
    private String desc;

    private ApiStatus(ApiStatusType statusType) {
        this.code = statusType.getCode();
        this.desc = statusType.getDesc();
    }

    public static ApiStatus of(ApiStatusType statusType) {
        return new ApiStatus(statusType);
    }

    public static ApiStatus success() {
        return ApiStatus.of(ApiStatusType.SUCCESS);
    }

    public static ApiStatus serverError() {
        return ApiStatus.of(ApiStatusType.FAIL_SERVER_ERROR);
    }

    public static ApiStatus validationError() {
        return ApiStatus.of(ApiStatusType.FAIL_VALIDATION_ERROR);
    }

}
