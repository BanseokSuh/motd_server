package com.banny.motd.api.service.user;

import com.banny.motd.api.service.user.request.UserJoinServiceRequest;
import com.banny.motd.api.service.user.request.UserLoginServiceRequest;
import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.Tokens;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.global.email.EmailHandler;
import com.banny.motd.global.exception.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private EmailHandler emailHandler;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
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
        UserLoginServiceRequest loginRequest = getUserLoginServiceRequest(loginId, password, device);
        userService.join(joinRequest); // 회원가입

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

    @Test
    void logout() {
        // given
        // when
        // then
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