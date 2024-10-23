package com.banny.motd.global.dto.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private ApiResponseStatus status;
    private T result;

    public static <T> ApiResponse<T> ok(T result) {
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.success())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> ok() {
        return ok(null);
    }

    public static <T> ApiResponse<T> error() {
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.error())
                .result(null)
                .build();
    }

    public String toStream() {
        return "{" +
                "\"status\":" + "{" +
                "\"code\":" + "\"" + ApiResponseStatusType.FAIL_INVALID_TOKEN.getCode() + "\"," +
                "\"desc\":" + "\"" + ApiResponseStatusType.FAIL_INVALID_TOKEN.getDesc() + "\"" + "}," +
                "\"result\":" + "{" +
                "\"message\":" + null + "}" +
                "}";
    }

}
