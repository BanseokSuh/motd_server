package com.banny.motd.api.service.user;

import com.banny.motd.api.service.user.request.UserJoinServiceRequest;
import com.banny.motd.api.service.user.request.UserLoginServiceRequest;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.global.enums.Device;

public interface UserService {

    User join(UserJoinServiceRequest request);

    Tokens login(UserLoginServiceRequest request);

    void logout(Long id, Device device);

    void delete(Long userId);

    User getMyInfo(Long userId);

    User loadUserByLoginId(String loginId);

    User getUserOrException(Long userId);

}
