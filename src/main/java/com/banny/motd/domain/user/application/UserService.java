package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.Tokens;
import com.banny.motd.domain.user.domain.User;

public interface UserService {

    User join(String loginId, String userName, String password, String gender);

    Tokens login(String loginId, String password);

    User getMyInfo(Long id);

    User loadUserByLoginId(String loginId);
}
