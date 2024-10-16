package com.banny.motd.api.service.user;

import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.enums.Device;

public interface UserService {

    User join(String loginId, String userName, String nickName, String password, String email, String gender);

    Tokens login(String loginId, String password, String deviceStr);

    void logout(Long id, Device device);

    void delete(Long userId);

    User getMyInfo(Long userId);

    User loadUserByLoginId(String loginId);

    User getUserOrException(Long userId);

}
