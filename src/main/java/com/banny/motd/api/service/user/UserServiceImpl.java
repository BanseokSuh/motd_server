package com.banny.motd.api.service.user;

import com.banny.motd.api.service.user.request.UserJoinServiceRequest;
import com.banny.motd.api.service.user.request.UserLoginServiceRequest;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.UserStatus;
import com.banny.motd.domain.user.infrastructure.UserCacheRepository;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import com.banny.motd.global.dto.response.ApiResponseStatusType;
import com.banny.motd.global.email.EmailHandler;
import com.banny.motd.global.enums.Device;
import com.banny.motd.global.exception.ApplicationException;
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
    public User join(UserJoinServiceRequest request) {
        // 이미 가입된 사용자인지 확인
        userRepository.findByLoginId(request.getLoginId()).ifPresent(userEntity -> {
            throw new ApplicationException(ApiResponseStatusType.FAIL_USER_DUPLICATED, String.format("User %s is duplicated", request.getLoginId()));
        });

        // 신규 유저 객체 생성
        User user = User.builder()
                .loginId(request.getLoginId())
                .userName(request.getUserName())
                .nickName(request.getNickName())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .userRole(UserRole.USER)
                .userStatus(UserStatus.PENDING)
                .build();
        user.setGenderStr(request.getGender());

        // 유저 저장
        User joinedUser = userRepository.save(UserEntity.from(user)).toDomain();

        // Welcome 이메일 발송
        emailHandler.sendWelcomeEmail(request.getEmail(), request.getLoginId());

        return joinedUser;
    }

    @Override
    @Transactional
    public Tokens login(UserLoginServiceRequest request) {
        // 로그인 아이디로 유저 조회
        User user = loadUserByLoginId(request.getLoginId());

        Device device = Device.from(request.getDevice());

        user.setDevice(device);

        // 비밀번호 일치 여부 확인
        user.checkPasswordMatch(request.getPassword(), encoder);

        // 이미 로그인된 사용자인지 확인
        userTokenManager.checkAlreadyLoggedIn(user.getId(), device);

        // 토큰 생성
        String accessToken = userTokenManager.generateAccessToken(user, device);
        String refreshToken = userTokenManager.generateRefreshToken(user, device);

        // 토큰 저장
        userTokenManager.saveAccessToken(user.getId(), device, accessToken);
        userTokenManager.saveRefreshToken(user.getId(), device, refreshToken);

        // 유저 정보 캐시 저장
        userCacheRepository.setUser(user);

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(Long id, Device device) {
        // 유저 로그아웃
        userTokenManager.deleteToken(id, device);

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
                        .orElseThrow(() -> new ApplicationException(ApiResponseStatusType.FAIL_USER_NOT_FOUND, String.format("User %d is not found", id)))
        );
    }

    @Override
    public User loadUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ApiResponseStatusType.FAIL_USER_NOT_FOUND, String.format("User %s is not found", loginId)));
    }

    @Override
    public User getUserOrException(Long userId) {
        return userRepository.findById(userId)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ApplicationException(ApiResponseStatusType.FAIL_USER_NOT_FOUND, String.format("UserId %s is not found", userId)));
    }

}
