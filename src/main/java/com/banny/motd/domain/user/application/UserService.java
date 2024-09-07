package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.Tokens;
import com.banny.motd.domain.user.domain.User;

public interface UserService {

    User join(String loginId, String userName, String password, String email, String gender);

    Tokens login(String loginId, String password, String deviceTypeStr);

    void logout(Long id, String deviceTypeStr);

    void delete(Long id);

    User getMyInfo(Long id);

    User loadUserByLoginId(String loginId);

}
