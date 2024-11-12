package com.banny.motd.global.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private ApiStatus status;
    private T result;

    @Builder
    private ApiResponse(ApiStatus status, T result) {
        this.status = status;
        this.result = result;
    }

    public static <T> ApiResponse<T> of(ApiStatus status, T result) {
        return ApiResponse.<T>builder().status(status).result(result).build();
    }

    public static <T> ApiResponse<T> of(ApiStatus status) {
        return of(status, null);
    }

    public static <T> ApiResponse<T> ok(T result) {
        return of(ApiStatus.success(), result);
    }

    public static <T> ApiResponse<T> ok() {
        return ok(null);
    }

    public static String toStream(ApiStatus status, String message) {
        return "{" +
                "\"status\":" + "{" +
                "\"code\":" + "\"" + status.getCode() + "\"," +
                "\"desc\":" + "\"" + status.getDesc() + "\"" + "}," +
                "\"result\":" + "{" +
                "\"message\":" + "\"" + message + "\"" + "}" +
                "}";
    }

}
