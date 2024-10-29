package com.banny.motd.api.service.user;

import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.User;

public class UserFixture {

    public static User get(String loginId, String userName, String nickName, String email, String password, String gender) {
        return User.builder()
                .loginId(loginId)
                .userName(userName)
                .nickName(nickName)
                .email(email)
                .password(password)
                .gender(Gender.from(gender))
                .build();
    }

}
