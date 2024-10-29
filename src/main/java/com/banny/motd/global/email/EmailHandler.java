package com.banny.motd.global.email;

import com.banny.motd.global.exception.ApplicationException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static com.banny.motd.global.exception.ApiResponseStatusType.FAIL_EMAIL_MIME_MESSAGE_HELPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailHandler {

    @Value("${spring.mail.from}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Welcome 이메일 비동기 발송
     * 해당 메서드는 messagePoolTaskExecutor 빈을 사용하여 비동기로 동작
     *
     * @param to 수신자 이메일 주소
     * @param loginId 수신자 로그인 아이디
     */
    @Async("messagePoolTaskExecutor")
    public void sendWelcomeEmail(String to, String loginId) {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Welcome to MOTD");
            helper.setText(setContext("welcomeEmail", loginId), true);
        } catch (Exception e) {
            log.error("Failed to set MimeMessageHelper", e);
            throw new ApplicationException(FAIL_EMAIL_MIME_MESSAGE_HELPER);
        }

        javaMailSender.send(message);
    }

    /**
     * Thymeleaf 템플릿 엔진을 사용하여 HTML 템플릿을 렌더링
     * resources/templates/welcomeEmail.html 템플릿을 렌더링
     *
     * @param type 템플릿 파일명
     * @param loginId 로그인 아이디
     * @return 렌더링된 HTML 문자열
     */
    public String setContext(String type, String loginId) {
        Context context = new Context();
        context.setVariable("loginId", loginId);
        return templateEngine.process(type, context);
    }
}
