package com.banny.motd.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private ApiResponseStatus status;
    private T result;

    public static <T> ApiResponse<T> ok(T result) {
        return new ApiResponse<>(ApiResponseStatus.success(), result);
    }

    public static <T> ApiResponse<T> ok() {
        return ok(null);
    }

    public static <T> ApiResponse<T> of(ApiResponseStatus status, T result) {
        return new ApiResponse<>(status, result);
    }

    public static <T> ApiResponse<T> of(ApiResponseStatus status) {
        return of(status, null);
    }

    public static String toStream(ApiResponseStatus status, String message) {
        return "{" +
                "\"status\":" + "{" +
                "\"code\":" + "\"" + status.getCode() + "\"," +
                "\"desc\":" + "\"" + status.getDesc() + "\"" + "}," +
                "\"result\":" + "{" +
                "\"message\":" + "\"" + message + "\"" + "}" +
                "}";
    }

}
