package com.lightcc.motd.domain.user.application;

import com.lightcc.motd.domain.user.domain.User;

public interface UserService {

    String helloUser();

    String helloUserException();

    User join(String loginId, String userName, String password);

    String login(String loginId, String password);
}
