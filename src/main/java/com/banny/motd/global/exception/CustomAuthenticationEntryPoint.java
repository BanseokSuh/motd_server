package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * SpringSecurity의 인증을 거치지 못했을 경우 반환할 예외 정의
 * String 타입으로 반환해야 하기 때문에 결과 객체를 String으로 변환하여 반환
 * - Response.error().toStream()
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.error("########### Authentication failed");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(Response.error().toStream());
    }

}
