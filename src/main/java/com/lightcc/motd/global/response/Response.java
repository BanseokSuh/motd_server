package com.lightcc.motd.global.response;

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
}
