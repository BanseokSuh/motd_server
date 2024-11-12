package com.banny.motd.global.enums;

import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Device {

    WEB(1, "WEB"),
    MOBILE_ANDROID(2, "MOBILE_ANDROID"),
    MOBILE_IOS(3, "MOBILE_IOS");

    private final int value;
    private final String name;

    public static Device from(String name) {
        for (Device device : Device.values()) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        throw new ApplicationException(ApiStatusType.FAIL_NOT_EXIST_DEVICE, "Device not found. name: " + name);
    }

}
