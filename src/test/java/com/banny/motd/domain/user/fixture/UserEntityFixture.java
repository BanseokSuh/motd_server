package com.banny.motd.domain.user.fixture;

import com.banny.motd.domain.user.domain.Gender;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String loginId, String userName, String password, String gender) {
        UserEntity result = new UserEntity();
        result.setLoginId(loginId);
        result.setUserName(userName);
        result.setPassword(password);
        result.setGender(Gender.valueOf(gender));

        return result;
    }
}
