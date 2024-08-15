package com.banny.motd.global.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailHandler {

    private final JavaMailSender javaMailSender;

    @Async("messagePoolTaskExecutor")
    public void sendMail(String email) {
        log.info("Send email to {} in thread {}", email, Thread.currentThread().getName());
    }
}
