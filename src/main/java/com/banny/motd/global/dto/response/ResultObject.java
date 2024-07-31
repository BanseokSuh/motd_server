package com.banny.motd.global.dto.response;

import com.banny.motd.global.exception.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
        return new ResultObject(ResultType.SERVER_ERROR);
    }

    public static ResultObject validateError() {
        return new ResultObject(ResultType.VALIDATE_ERROR);
    }
}
