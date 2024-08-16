package com.banny.motd.global.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailHandler {

    @Value("${spring.mail.from}")
    private String from;

    private final JavaMailSender javaMailSender;

    @Async("messagePoolTaskExecutor")
    public void sendWelcomeEmail(String to, String loginId) {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Welcome to MOTD");
            helper.setText(String.format("Hello %s!\nWelcome to MOTD!\nWe are happy to have you as a member of our community.", loginId));
        } catch (Exception e) {
            log.error("Failed to set MimeMessageHelper", e);
        }

        javaMailSender.send(message);
    }
}
