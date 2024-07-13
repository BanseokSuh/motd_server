package com.lightcc.motd.domain.user.application;

import com.lightcc.motd.domain.user.domain.User;
import com.lightcc.motd.domain.user.domain.UserRole;
import com.lightcc.motd.domain.user.domain.repository.UserRepository;
import com.lightcc.motd.domain.user.infrastructure.entity.UserEntity;
import com.lightcc.motd.global.exception.ApplicationException;
import com.lightcc.motd.global.exception.ResultType;
import com.lightcc.motd.global.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.access-expired-time-ms}")
    private Long accessExpiredTimeMs;

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

        userRepository.findByLoginId(loginId).ifPresent(userEntity -> {
            throw new ApplicationException(ResultType.USER_DUPLICATED, String.format("User %s is duplicated", loginId));
        });

        User user = new User();
        user.setLoginId(loginId);
        user.setUserName(userName);
        user.setPassword(encoder.encode(password));
        user.setRole(UserRole.USER);

        UserEntity userEntity = userRepository.save(UserEntity.from(user));

        return userEntity.toDomain();
    }

    @Override
    public String login(String loginId, String password) {
        UserEntity userEntity = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("User %s is not found", loginId)));

        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new ApplicationException(ResultType.USER_PASSWORD_MISMATCH, "Password mismatch");
        }

        return JwtTokenUtils.generateToken(userEntity.toDomain(), secretKey, accessExpiredTimeMs);
    }
}
