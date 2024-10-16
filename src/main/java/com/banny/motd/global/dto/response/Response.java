package com.banny.motd.global.dto.response;

import com.banny.motd.global.exception.StatusType;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private StstusObject status;
    private T result;

    public static <T> Response<T> success(T result) {
        return Response.<T>builder()
                .status(StstusObject.success())
                .result(result)
                .build();
    }

    public static <T> Response<T> success() {
        return Response.<T>builder()
                .status(StstusObject.success())
                .result(null)
                .build();
    }

    public static <T> Response<T> error() {
        return Response.<T>builder()
                .status(StstusObject.error())
                .result(null)
                .build();
    }

    public String toStream() {
        return "{" +
                "\"status\":" + "{" +
                "\"code\":" + "\"" + StatusType.FAIL_INVALID_TOKEN.getCode() + "\"," +
                "\"desc\":" + "\"" + StatusType.FAIL_INVALID_TOKEN.getDesc() + "\"" + "}," +
                "\"result\":" + "{" +
                "\"message\":" + null + "}" +
                "}";
    }

}
