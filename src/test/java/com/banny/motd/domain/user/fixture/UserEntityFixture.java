package com.banny.motd.domain.user.fixture;

import com.banny.motd.domain.user.domain.Gender;
import com.banny.motd.domain.user.domain.UserRole;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String loginId, String userName, String password, String email, String gender, String userRole) {
        return UserEntity.builder()
                .loginId(loginId)
                .userName(userName)
                .password(password)
                .email(email)
                .gender(Gender.valueOf(gender))
                .userRole(UserRole.valueOf(userRole))
                .build();
    }
}
