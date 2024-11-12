package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.ApiResponse;
import com.banny.motd.global.dto.response.ApiStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(ApiResponse.toStream(
                ApiStatus.of(ApiStatusType.FAIL_INVALID_TOKEN), "Token is invalid or expired."));
    }

}
