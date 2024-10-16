package com.banny.motd.domain.user.application;


import com.banny.motd.api.service.user.UserService;
import com.banny.motd.configuration.TestContainerConfiguration;
import com.banny.motd.domain.user.infrastructure.UserRepository;
import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.fixture.UserEntityFixture;
import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import com.banny.motd.global.email.EmailHandler;
import com.banny.motd.global.enums.Device;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.StatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(TestContainerConfiguration.class)
@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @MockBean
    private EmailHandler emailHandler;

    @Test
    @DisplayName("회원가입")
    void join() {
        // given
        String loginId = "loginId_test";
        String userName = "userName_test";
        String nickName = "nickName_test";
        String password = "password_test";
        String email = "still3028@naver.com";
        String gender = Gender.MALE.name();
        String userRole = UserRole.USER.name();

        // mock
        when(userRepository.findByLoginId(loginId)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(UserEntityFixture.get(loginId, userName, nickName, password, email, gender, userRole));
        doNothing().when(emailHandler).sendWelcomeEmail(email, loginId);

        // when, then
        assertDoesNotThrow(() -> userService.join(loginId, userName, nickName, password, email, gender));
        verify(emailHandler, times(1)).sendWelcomeEmail(email, loginId);
    }

    @Test
    @DisplayName("회원가입시_loginId로_회원가입한_유저가_이미_있다")
    void join_already_exist_loginId() {
        // given
        String loginId = "loginId_test";
        String userName = "userName_test";
        String nickName = "nickName_test";
        String password = "password_test";
        String email = "still3028@naver.com";
        String gender = Gender.MALE.name();
        String userRole = UserRole.USER.name();

        UserEntity fixture = UserEntityFixture.get(loginId, userName, nickName, password, email, gender, userRole);

        // mock
        when(userRepository.findByLoginId(loginId)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(UserEntityFixture.get(loginId, userName, nickName, password, email, gender, userRole));

        // when, then
        ApplicationException e = assertThrows(ApplicationException.class, () -> userService.join(loginId, userName, nickName, email, password, gender));
        assertEquals(StatusType.FAIL_USER_DUPLICATED.getCode(), e.getStatus().getCode());
    }

    @Test
    @DisplayName("로그인")
    void login() {
        // given
        String loginId = "loginId_test";
        String userName = "userName_test";
        String nickName = "nickName_test";
        String password = "password_test";
        String email = "still3028@gmail.com";
        String gender = Gender.MALE.name();
        String userRole = UserRole.USER.name();
        String deviceStr = Device.WEB.name();

        UserEntity fixture = UserEntityFixture.get(loginId, userName, nickName, password, email, gender, userRole);

        // mock
        when(userRepository.findByLoginId(loginId)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        // when, then
        assertDoesNotThrow(() -> userService.login(loginId, password, deviceStr));
    }

    @Test
    @DisplayName("로그인시_아이디를_찾을수없다")
    void login_not_found_user() {
        // given
        String loginId = "loginId_test";
        String password = "password";
        String deviceStr = Device.WEB.name();

        // mock
        when(userRepository.findByLoginId(loginId)).thenReturn(Optional.empty());

        // when, then
        ApplicationException e = assertThrows(ApplicationException.class, () -> userService.login(loginId, password, deviceStr));
        assertEquals(StatusType.FAIL_USER_NOT_FOUND.getCode(), e.getStatus().getCode());
    }

    @Test
    @DisplayName("로그인시_비밀번호가_맞지_않다")
    void login_password_mismatch() {
        // given
        String loginId = "loginId_test";
        String userName = "userName_test";
        String nickName = "nickName_test";
        String password = "password";
        String email = "still3028@gmail.com";
        String gender = Gender.MALE.name();
        String userRole = UserRole.USER.name();
        String deviceStr = Device.WEB.name();

        UserEntity fixture = UserEntityFixture.get(loginId, userName, nickName, password, email, gender, userRole);

        // mock
        when(userRepository.findByLoginId(loginId)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(false);

        // when, then
        ApplicationException e = assertThrows(ApplicationException.class, () -> userService.login(loginId, password, deviceStr));
        assertEquals(StatusType.FAIL_USER_PASSWORD_MISMATCH.getCode(), e.getStatus().getCode());
    }

}