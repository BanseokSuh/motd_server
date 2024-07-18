package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.Gender;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.domain.user.domain.UserRole;
import com.banny.motd.domain.user.domain.repository.UserRepository;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import com.banny.motd.global.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
    public User join(String loginId, String userName, String password, String gender) {

        userRepository.findByLoginId(loginId).ifPresent(userEntity -> {
            throw new ApplicationException(ResultType.USER_DUPLICATED, String.format("User %s is duplicated", loginId));
        });

        User user = new User();
        user.setLoginId(loginId);
        user.setUserName(userName);
        user.setPassword(encoder.encode(password));
        user.setRole(UserRole.USER);
        user.setGender(Gender.valueOf(gender));

        return userRepository.save(UserEntity.from(user)).toDomain();
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

    @Override
    public User loadUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("User %s is not found", loginId)))
                .toDomain();
    }
}
