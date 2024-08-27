package com.banny.motd.global.dto.response;

import com.banny.motd.global.exception.ResultType;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private ResultObject result;
    private T data;

    public static <T> Response<T> success(T result) {
        return Response.<T>builder()
                .result(ResultObject.success())
                .data(result)
                .build();
    }

    public static <T> Response<T> success() {
        return Response.<T>builder()
                .result(ResultObject.success())
                .data(null)
                .build();
    }

    public static <T> Response<T> error() {
        return Response.<T>builder()
                .result(ResultObject.error())
                .data(null)
                .build();
    }

    public String toStream() {
        return "{" +
                "\"result\":" + "{" +
                "\"code\":" + "\"" + ResultType.FAIL_INVALID_TOKEN.getCode() + "\"," +
                "\"desc\":" + "\"" + ResultType.FAIL_INVALID_TOKEN.getDesc() + "\"" + "}," +
                "\"data\":" + "{" +
                "\"message\":" + null + "}" +
                "}";
    }

}
