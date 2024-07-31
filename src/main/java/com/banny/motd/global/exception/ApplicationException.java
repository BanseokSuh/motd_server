package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ResultObject;
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

    public ApplicationException(ResultType resultType) {
        this.result = new ResultObject(resultType);
        this.data = new ErrorResult(null);
    }
}
