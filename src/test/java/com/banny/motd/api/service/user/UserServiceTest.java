package com.banny.motd.api.service.user;

import com.banny.motd.api.service.user.request.UserJoinServiceRequest;
import com.banny.motd.api.service.user.request.UserLoginServiceRequest;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.email.EmailHandler;
import com.banny.motd.global.enums.Device;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@Sql(executionPhase = AFTER_TEST_METHOD, scripts = {"/sql/reset.sql"})
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
        UserJoinServiceRequest request = getUserJoinServiceRequest(loginId, userName, nickName, email, password);

        // when
        User joinedUser = userService.join(request);

        // then
        assertThat(joinedUser)
                .extracting("loginId", "userName", "nickName", "email")
                .contains(loginId, userName, loginId, email);

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
        UserJoinServiceRequest request = getUserJoinServiceRequest(loginId, userName, nickName, email, password);
        userService.join(request); // 1회 회원가입

        // when // then
        assertThatThrownBy(() -> userService.join(request))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiStatusType.FAIL_USER_DUPLICATED.getCode(),
                        ApiStatusType.FAIL_USER_DUPLICATED.getDesc(),
                        String.format("User %s is duplicated", loginId)
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
        String device = "WEB";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password);
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
                        ApiStatusType.FAIL_USER_NOT_FOUND.getCode(),
                        ApiStatusType.FAIL_USER_NOT_FOUND.getDesc(),
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
        String device = "WRONG_DEVICE";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password);
        userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);

        // when // then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiStatusType.FAIL_NOT_EXIST_DEVICE.getCode(),
                        ApiStatusType.FAIL_NOT_EXIST_DEVICE.getDesc(),
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
        String device = "WEB";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, wrongPassword);
        userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);

        // when // then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(ApplicationException.class)
                .extracting("status.code", "status.desc", "result.message")
                .contains(
                        ApiStatusType.FAIL_USER_PASSWORD_MISMATCH.getCode(),
                        ApiStatusType.FAIL_USER_PASSWORD_MISMATCH.getDesc(),
                        "Password does not match"
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
        String device = "WEB";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password);
        User joinedUser = userService.join(joinRequest); // 회원가입

        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);
        userService.login(loginRequest); // 로그인

        // when
        userService.logout(joinedUser.getId(), Device.from(device));

        // then
        assertThat(redisTemplate.hasKey("A_TOKEN:" + joinedUser.getId() + "-" + device)).isFalse();
        assertThat(redisTemplate.hasKey("R_TOKEN:" + joinedUser.getId() + "-" + device)).isFalse();
        assertThat(redisTemplate.hasKey("USER:" + joinedUser.getId())).isFalse();
    }

    @DisplayName("회원탈퇴가 정상적으로 동작한다.")
    @Test
    void delete() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        String device = "WEB";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password);
        User joinedUser = userService.join(joinRequest); // 회원가입

        // when
        userService.delete(joinedUser.getId());

        // then
        assertThat(userRepository.findById(joinedUser.getId())).isEmpty();
        assertThat(redisTemplate.hasKey("A_TOKEN:" + joinedUser.getId() + "-" + device)).isFalse();
        assertThat(redisTemplate.hasKey("R_TOKEN:" + joinedUser.getId() + "-" + device)).isFalse();
        assertThat(redisTemplate.hasKey("USER:" + joinedUser.getId())).isFalse();
    }

    @DisplayName("내 정보 조회가 정상적으로 동작한다.")
    @Test
    void getMyInfo() {
        // given
        String loginId = "test000";
        String userName = "서반석";
        String nickName = "반석";
        String email = "still3028@gmail.com";
        String password = "test001!";
        UserJoinServiceRequest joinRequest = getUserJoinServiceRequest(loginId, userName, nickName, email, password);
        User joinedUser = userService.join(joinRequest); // 회원가입

        // when
        User myUser = userService.getMyInfo(joinedUser.getId());

        // then
        assertThat(myUser)
                .extracting("loginId", "userName", "nickName", "email")
                .contains(loginId, userName, nickName, email);
    }

    private static UserLoginServiceRequest getUserLoginServiceRequest(String loginId, String password, String device) {
        return UserLoginServiceRequest.builder()
                .loginId(loginId)
                .password(password)
                .device(device)
                .build();
    }

    private static UserJoinServiceRequest getUserJoinServiceRequest(String loginId, String userName, String nickName, String email, String password) {
        return UserJoinServiceRequest.builder()
                .loginId(loginId)
                .userName(userName)
                .nickName(nickName)
                .email(email)
                .password(password)
                .build();
    }

}