package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.application.repository.UserCacheRepository;
import com.banny.motd.domain.user.application.repository.UserTokenManager;
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
    private final UserCacheRepository userCacheRepository;
    private final UserTokenManager userTokenManager;

    @Override
    @Transactional
    public User join(String loginId, String userName, String password, String email, String gender) {

        // 이미 가입된 사용자인지 확인
        userRepository.findByLoginId(loginId).ifPresent(userEntity -> {
            throw new ApplicationException(ResultType.USER_DUPLICATED, String.format("User %s is duplicated", loginId));
        });

        // 신규 유저 객체 생성
        User user = User.builder()
                .loginId(loginId)
                .userName(userName)
                .password(encoder.encode(password))
                .email(email)
                .userRole(UserRole.USER)
                .build();
        user.setGenderString(gender);

        // 유저 저장
        return userRepository.save(UserEntity.from(user)).toDomain();
    }


    @Override
    @Transactional
    public Tokens login(String loginId, String password) {

        // 로그인 아이디로 유저 조회
        User user = loadUserByLoginId(loginId);

        // 비밀번호 일치 여부 확인
        if (!encoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ResultType.USER_PASSWORD_MISMATCH, "Password mismatch");
        }

        // 토큰 생성
        String accessToken = userTokenManager.generateAccessToken(user);
        String refreshToken = userTokenManager.generateRefreshToken(user);

        // refresh token 저장
        userTokenManager.saveAccessToken(user.getId(), accessToken);
        userTokenManager.saveRefreshToken(user.getId(), refreshToken);

        // 유저 정보 캐시 저장
        userCacheRepository.setUser(user);

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public User getMyInfo(Long id) {
        return userCacheRepository.getUser(id).orElseGet(() ->
                userRepository.findById(id)
                        .map(UserEntity::toDomain)
                        .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("User %s is not found", id)))
                );
    }

    @Override
    public User loadUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("User %s is not found", loginId)));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ResultType.USER_NOT_FOUND, String.format("User %s is not found", id)));
    }
}
