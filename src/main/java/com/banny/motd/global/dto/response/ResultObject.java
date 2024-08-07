package com.banny.motd.global.dto.response;

import com.banny.motd.global.exception.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject {

    private String code;
    private String desc;

    public ResultObject(ResultType resultType) {
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
    }

    public static ResultObject success() {
        return new ResultObject(ResultType.SUCCESS);
    }

    public static ResultObject error() {
        return new ResultObject(ResultType.FAIL_SERVER_ERROR);
    }

    public static ResultObject validateError() {
        return new ResultObject(ResultType.FAIL_VALIDATE_ERROR);
    }
}
