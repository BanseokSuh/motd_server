package com.banny.motd.global.enums;

import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Device {

    WEB(1, "WEB"),
    MOBILE_ANDROID(2, "MOBILE_ANDROID"),
    MOBILE_IOS(3, "MOBILE_IOS");

    private final int value;
    private final String name;

    /**
     * 디바이스 타입 반환
     *
     * @param name 디바이스 타입 이름
     * @return 디바이스 타입
     */
    public static Device from(String name) {
        for (Device device : Device.values()) {
            if (device.getName().equals(name)) {
                return device;
            }
        }

        throw new ApplicationException(ResultType.FAIL_NOT_EXIST_DEVICE, "Device not found. name: " + name);
    }

}
