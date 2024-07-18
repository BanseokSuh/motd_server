package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.User;

public interface UserService {

    String helloUser();

    String helloUserException();

    User join(String loginId, String userName, String password, String gender);

    String login(String loginId, String password);

    User loadUserByLoginId(String loginId);
}
