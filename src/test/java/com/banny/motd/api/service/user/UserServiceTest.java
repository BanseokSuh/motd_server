package com.banny.motd.api.service.user;

import com.banny.motd.api.service.user.request.UserJoinServiceRequest;
import com.banny.motd.api.service.user.request.UserLoginServiceRequest;
import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.email.EmailHandler;
import com.banny.motd.global.enums.Device;
import com.banny.motd.global.exception.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @MockBean
    private EmailHandler emailHandler;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
    }

    @DisplayName("회원가입이 정상적으로 동작한다.")
    @Test
    void join() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String gender = "MALE";
        UserJoinServiceRequest request = getUserJoinServiceRequest(loginId, userName, nickName, email, password, gender);

        // when
        User joinedUser = userService.join(request);

        // then
        assertThat(joinedUser)
                .extracting("loginId", "userName", "nickName", "email", "gender")
                .contains(loginId, userName, loginId, email, Gender.from(gender));

        verify(emailHandler, times(1)).sendWelcomeEmail(any(), any());
    }

    @DisplayName("회원가입 시 중복된 로그인 아이디로 요청이 들어오면 예외가 발생한다.")
    @Test
    void joinWithDuplicatedLoginId() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String gender = "MALE";
        UserJoinServiceRequest request = getUserJoinServiceRequest(loginId, userName, nickName, email, password, gender);
        userService.join(request); // 1회 회원가입

        // when // then
        assertThatThrownBy(() -> userService.join(request))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiResponseStatusType.FAIL_USER_DUPLICATED.getCode(),
                        ApiResponseStatusType.FAIL_USER_DUPLICATED.getDesc(),
                        String.format("User %s is duplicated", loginId)
                );
    }

    @DisplayName("회원가입 시 잘못된 성별로 요청이 들어오면 예외가 발생한다.")
    @Test
    void joinWithWrongGender() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String gender = "WRONG_GENDER";
        UserJoinServiceRequest request = getUserJoinServiceRequest(loginId, userName, nickName, email, password, gender);

        // when // then
        assertThatThrownBy(() -> userService.join(request))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiResponseStatusType.FAIL_INVALID_PARAMETER.getCode(),
                        ApiResponseStatusType.FAIL_INVALID_PARAMETER.getDesc(),
                        "Invalid gender"
                );
    }

    @DisplayName("로그인이 정상적으로 동작한다.")
    @Test
    void login() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String gender = "MALE";
        String device = "WEB";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password, gender);
        userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);

        // when
        Tokens token = userService.login(loginRequest);

        // then
        assertThat(token)
                .extracting("accessToken", "refreshToken")
                .isNotNull();
    }

    @DisplayName("로그인 시 존재하지 않는 로그인 아이디로 요청이 들어오면 예외가 발생한다.")
    @Test
    void loginWithNotExistLoginId() {
        // given
        String loginId = "test000";
        String password = "test001!";
        String device = "WEB";
        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);

        // when // then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiResponseStatusType.FAIL_USER_NOT_FOUND.getCode(),
                        ApiResponseStatusType.FAIL_USER_NOT_FOUND.getDesc(),
                        String.format("User %s is not found", loginId)
                );
    }

    @DisplayName("로그인 시 잘못된 디바이스 타입을 입력하면 예외가 발생한다.")
    @Test
    void loginWithWrongDeviceType() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String gender = "MALE";
        String device = "WRONG_DEVICE";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password, gender);
        userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);

        // when // then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiResponseStatusType.FAIL_NOT_EXIST_DEVICE.getCode(),
                        ApiResponseStatusType.FAIL_NOT_EXIST_DEVICE.getDesc(),
                        String.format("Device not found. name: %s", device)
                );
    }

    @DisplayName("로그인 시 비밀번호가 틀린 경우 예외가 발생한다.")
    @Test
    void loginWithWrongPassword() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String wrongPassword = "wrong_password";
        String gender = "MALE";
        String device = "WEB";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, wrongPassword, gender);
        userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);

        // when // then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiResponseStatusType.FAIL_USER_PASSWORD_MISMATCH.getCode(),
                        ApiResponseStatusType.FAIL_USER_PASSWORD_MISMATCH.getDesc(),
                        "Password does not match"
                );
    }

    @DisplayName("로그인 시 이미 로그인된 사용자가 요청이 들어오면 예외가 발생한다.")
    @Test
    void loginWithAlreadyLoggedInUser() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String gender = "MALE";
        String device = "WEB";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password, gender);
        userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);
        userService.login(loginRequest); // 1회 로그인

        // when // then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiResponseStatusType.FAIL_ALREADY_LOGGED_IN.getCode(),
                        ApiResponseStatusType.FAIL_ALREADY_LOGGED_IN.getDesc(),
                        String.format("User %s is already logged in", loginId)
                );
    }

    @DisplayName("로그아웃이 정상적으로 동작한다.")
    @Test
    void logout() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String gender = "MALE";
        String deviceStr = "WEB";
        Device device = Device.from(deviceStr);
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password, gender);
        User joinedUser = userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, deviceStr);
        userService.login(loginRequest); // 로그인

        // when
        userService.logout(joinedUser.getId(), device);

        // then
        assertThat(redisTemplate.hasKey("A_TOKEN:" + joinedUser.getId() + "-" + device.getValue())).isFalse();
        assertThat(redisTemplate.hasKey("R_TOKEN:" + joinedUser.getId() + "-" + device.getValue())).isFalse();
        assertThat(redisTemplate.hasKey("USER:" + joinedUser.getId())).isFalse();
    }

    @Test
    void delete() {
        // given
        // when
        // then
    }

    @Test
    void getMyInfo() {
        // given
        // when
        // then
    }

    private static UserLoginServiceRequest getUserLoginServiceRequest(String loginId, String password, String device) {
        return UserLoginServiceRequest.builder()
                .loginId(loginId)
                .password(password)
                .device(device)
                .build();
    }

    private static UserJoinServiceRequest getUserJoinServiceRequest(String loginId, String userName, String nickName, String email, String password, String gender) {
        return UserJoinServiceRequest.builder()
                .loginId(loginId)
                .userName(userName)
                .nickName(nickName)
                .email(email)
                .password(password)
                .gender(gender)
                .build();
    }

}