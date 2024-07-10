package com.lightcc.motd.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
