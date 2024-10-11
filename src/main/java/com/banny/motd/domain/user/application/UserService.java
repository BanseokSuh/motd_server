package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.Tokens;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.enums.Device;

public interface UserService {

    User join(String loginId, String userName, String password, String email, String gender);

    Tokens login(String loginId, String password, String deviceStr);

    void logout(Long id, Device device);

    void delete(Long userId);

    User getMyInfo(Long userId);

    User loadUserByLoginId(String loginId);

    User getUserOrException(Long userId);

}
