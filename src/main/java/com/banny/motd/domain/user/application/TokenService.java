package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.User;

public interface TokenService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    void saveRefreshToken(Long userId, String refreshToken);

    boolean validateAccessToken(String token);

    boolean validateRefreshToken(String token);
}
