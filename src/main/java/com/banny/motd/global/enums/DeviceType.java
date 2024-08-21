package com.banny.motd.global.enums;

import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {
    WEB(1, "web"),
    MOBILE_ANDROID(2, "mobile_android"),
    MOBILE_IOS(3, "mobile_ios");

    private final Integer value;
    private final String name;

    /**
     * 디바이스 타입 반환
     *
     * @param name 디바이스 타입 이름
     * @return 디바이스 타입
     */
    public static DeviceType from(String name) {
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.getName().equals(name)) {
                return deviceType;
            }
        }

        throw new ApplicationException(ResultType.FAIL_NOT_EXIST_DEVICE_TYPE, "존재하지 않는 디바이스 타입입니다.");
    }
}
