package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.Gender;
import com.banny.motd.domain.user.domain.Tokens;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.domain.user.domain.UserRole;
import com.banny.motd.domain.user.application.repository.UserRepository;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenService jwtTokenService;

    @Override
    @Transactional
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
    @Transactional
    public Tokens login(String loginId, String password) {
        UserEntity userEntity = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("User %s is not found", loginId)));

        User user = userEntity.toDomain();

        if (!encoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ResultType.USER_PASSWORD_MISMATCH, "Password mismatch");
        }

        String accessToken = jwtTokenService.generateAccessToken(user);
        String refreshToken = jwtTokenService.generateRefreshToken(user);

        jwtTokenService.saveRefreshToken(user.getId(), refreshToken);

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public User loadUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("User %s is not found", loginId)))
                .toDomain();
    }
}
