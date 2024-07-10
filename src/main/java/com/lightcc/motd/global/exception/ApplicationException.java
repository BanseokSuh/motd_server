package com.lightcc.motd.global.exception;

import com.lightcc.motd.global.response.ResultObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private ResultObject result;
    private ErrorResult data;

    public ApplicationException(ResultType resultType, String message) {
        this.result = new ResultObject(resultType);
        this.data = new ErrorResult(message);
    }
}
