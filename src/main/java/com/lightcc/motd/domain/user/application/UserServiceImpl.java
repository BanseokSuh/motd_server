package com.lightcc.motd.domain.user.application;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String helloUser() {
        return "helloUser";
    }
}
