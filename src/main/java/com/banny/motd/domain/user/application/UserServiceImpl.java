package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.application.repository.UserCacheRepository;
import com.banny.motd.domain.user.application.repository.UserTokenManager;
import com.banny.motd.domain.user.domain.Tokens;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.domain.user.domain.UserRole;
import com.banny.motd.domain.user.application.repository.UserRepository;
import com.banny.motd.domain.user.domain.UserStatus;
import com.banny.motd.domain.user.infrastructure.entity.UserEntity;
import com.banny.motd.global.email.EmailHandler;
import com.banny.motd.global.enums.DeviceType;
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
    private final EmailHandler emailHandler;

    @Override
    @Transactional
    public User join(String loginId, String userName, String password, String email, String gender) {

        // 이미 가입된 사용자인지 확인
        userRepository.findByLoginId(loginId).ifPresent(userEntity -> {
            throw new ApplicationException(ResultType.FAIL_USER_DUPLICATED, String.format("User %s is duplicated", loginId));
        });

        // 신규 유저 객체 생성
        User user = User.builder()
                .loginId(loginId)
                .userName(userName)
                .password(encoder.encode(password))
                .email(email)
                .userRole(UserRole.USER)
                .userStatus(UserStatus.PENDING)
                .build();
        user.setGenderStr(gender);

        // 유저 저장
        User joinedUser = userRepository.save(UserEntity.from(user)).toDomain();

        // Welcome 이메일 발송
        emailHandler.sendWelcomeEmail(email, loginId);

        return joinedUser;
    }


    @Override
    @Transactional
    public Tokens login(String loginId, String password, String deviceTypeStr) {
        // 로그인 아이디로 유저 조회
        User user = loadUserByLoginId(loginId);

        // 비밀번호 일치 여부 확인
        if (!encoder.matches(password, user.getPassword())) {
            throw new ApplicationException(ResultType.FAIL_USER_PASSWORD_MISMATCH, "Password mismatch");
        }

        // 디바이스 타입 객체 생성
        DeviceType deviceType = DeviceType.from(deviceTypeStr);

        // 이미 로그인된 사용자인지 확인
        userTokenManager.checkAlreadyLoggedIn(user.getId(), deviceType);

        // 토큰 생성
        String accessToken = userTokenManager.generateAccessToken(user);
        String refreshToken = userTokenManager.generateRefreshToken(user);

        // 토큰 저장
        userTokenManager.saveAccessToken(user.getId(), deviceType, accessToken);
        userTokenManager.saveRefreshToken(user.getId(), deviceType, refreshToken);

        // 유저 정보 캐시 저장
        userCacheRepository.setUser(user);

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(Long id, String deviceTypeStr) {
        // 디바이스 타입 객체 생성
        DeviceType deviceType = DeviceType.from(deviceTypeStr);

        // 유저 로그아웃
        userTokenManager.deleteToken(id, deviceType);

        // 유저 캐시 삭제
        userCacheRepository.deleteUser(id);
    }

    @Override
    public void delete(Long id) {

        // 유저 삭제
        userRepository.deleteById(id);

        // 유저 캐시 삭제
        userCacheRepository.deleteUser(id);
    }

    @Override
    public User getMyInfo(Long id) {
        return userCacheRepository.getUser(id).orElseGet(() ->
                userRepository.findById(id)
                        .map(UserEntity::toDomain)
                        .orElseThrow(() -> new ApplicationException(ResultType.FAIL_USER_NOT_FOUND, String.format("User %d is not found", id)))
                );
    }

    @Override
    public User loadUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ResultType.FAIL_USER_NOT_FOUND, String.format("User %s is not found", loginId)));
    }

}
