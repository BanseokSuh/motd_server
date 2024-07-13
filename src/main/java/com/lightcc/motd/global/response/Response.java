package com.lightcc.motd.global.response;

import com.lightcc.motd.global.exception.ResultType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {

    private ResultObject result;
    private T data;

    public static <T> Response<T> success(T result) {
        Response<T> response = new Response<>();
        response.setResult(ResultObject.success());
        response.setData(result);
        return response;
    }

    public static <T> Response<T> success() {
        Response<T> response = new Response<>();
        response.setResult(ResultObject.success());
        return response;
    }

    public static <T> Response<T> error() {
        Response<T> response = new Response<>();
        response.setResult(ResultObject.error());
        return response;
    }

    public String toStream() {
        return "{" +
                "\"result\":" + "{" +
                "\"code\":" + "\"" + ResultType.INVALID_TOKEN.getCode() + "\"," +
                "\"desc\":" + "\"" + ResultType.INVALID_TOKEN.getDesc() + "\"" + "}," +
                "\"data\":" + "{" +
                "\"message\":" + null + "}" +
                "}";
    }
}
