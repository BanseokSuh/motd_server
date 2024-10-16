package com.banny.motd.global.dto.response;

import com.banny.motd.global.exception.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StstusObject {

    private String code;
    private String desc;

    public StstusObject(StatusType statusType) {
        this.code = statusType.getCode();
        this.desc = statusType.getDesc();
    }

    public static StstusObject success() {
        return new StstusObject(StatusType.SUCCESS);
    }

    public static StstusObject error() {
        return new StstusObject(StatusType.FAIL_SERVER_ERROR);
    }

    public static StstusObject validateError() {
        return new StstusObject(StatusType.FAIL_VALIDATE_ERROR);
    }

}
