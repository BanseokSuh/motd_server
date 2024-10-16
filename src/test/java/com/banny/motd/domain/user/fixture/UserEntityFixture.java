package com.banny.motd.domain.user.fixture;

import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String loginId, String userName, String nickName , String password, String email, String gender, String userRole) {
        return UserEntity.builder()
                .loginId(loginId)
                .userName(userName)
                .nickName(nickName)
                .password(password)
                .email(email)
                .gender(Gender.valueOf(gender))
                .userRole(UserRole.valueOf(userRole))
                .build();
    }
}
