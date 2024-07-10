package com.lightcc.motd.global.response;

import com.lightcc.motd.global.exception.ResultType;
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
}
