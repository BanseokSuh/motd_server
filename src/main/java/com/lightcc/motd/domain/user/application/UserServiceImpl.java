package com.lightcc.motd.domain.user.application;

import com.lightcc.motd.domain.user.domain.User;
import com.lightcc.motd.domain.user.domain.UserRole;
import com.lightcc.motd.domain.user.domain.repository.UserRepository;
import com.lightcc.motd.domain.user.infrastructure.entity.UserEntity;
import com.lightcc.motd.global.exception.ApplicationException;
import com.lightcc.motd.global.exception.ResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public String helloUser() {
        return "helloUser";
    }

    @Override
    public String helloUserException() {
        throw new ApplicationException(ResultType.SERVER_ERROR, "helloUserException");
    }

    @Override
    public User join(String loginId, String userName, String password) {

        User user = new User();
        user.setLoginId(loginId);
        user.setUserName(userName);
        user.setPassword(encoder.encode(password));
        user.setRole(UserRole.USER);

        UserEntity userEntity = userRepository.save(UserEntity.from(user));

        return userEntity.toDomain();
    }
}
