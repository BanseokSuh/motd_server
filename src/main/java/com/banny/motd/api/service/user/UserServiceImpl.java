package com.banny.motd.api.service.user;

import com.banny.motd.api.service.user.request.UserJoinServiceRequest;
import com.banny.motd.api.service.user.request.UserLoginServiceRequest;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.UserStatus;
import com.banny.motd.domain.user.infrastructure.UserCacheRepository;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.email.EmailHandler;
import com.banny.motd.global.enums.Device;
import com.banny.motd.global.exception.ApiStatusType;
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
        userRepository.findByLoginId(request.getLoginId()).ifPresent(userEntity -> {
            throw new ApplicationException(ApiStatusType.FAIL_USER_DUPLICATED, String.format("User %s is duplicated", request.getLoginId()));
        });

        User user = User.builder()
                .loginId(request.getLoginId())
                .userName(request.getUserName())
                .nickName(request.getNickName())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .userRole(UserRole.USER)
                .userStatus(UserStatus.PENDING)
                .build();
        User joinedUser = userRepository.save(user);

        emailHandler.sendWelcomeEmail(request.getEmail(), request.getLoginId());

        return joinedUser;
    }

    @Override
    @Transactional
    public Tokens login(UserLoginServiceRequest request) {
        User user = userRepository.getByLoginId(request.getLoginId());

        Device device = Device.from(request.getDevice());
        user.setDevice(device);

        user.checkPasswordMatch(request.getPassword(), encoder);

        // 이미 로그인된 사용자인지 확인
//        userTokenManager.checkAlreadyLoggedIn(user, device);

        // 토큰 생성
        String accessToken = userTokenManager.generateAccessToken(user, device);
        String refreshToken = userTokenManager.generateRefreshToken(user, device);

        // 토큰 저장
        userTokenManager.saveAccessToken(user, device, accessToken);
        userTokenManager.saveRefreshToken(user, device, refreshToken);

        // 유저 정보 캐시 저장
        userCacheRepository.setUser(user);

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(Long id, Device device) {
        userTokenManager.deleteToken(id, device);
        userCacheRepository.deleteUser(id);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
        userCacheRepository.deleteUser(id);
    }

    @Override
    public User getMyInfo(Long id) {
        return userCacheRepository.getUser(id).orElseGet(() -> userRepository.getById(id));
    }

    @Override
    public User getByLoginId(String loginId) {
        return userRepository.getByLoginId(loginId);
    }

}
