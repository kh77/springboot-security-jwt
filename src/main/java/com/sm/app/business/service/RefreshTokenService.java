package com.sm.app.business.service;

import com.sm.app.orm.entity.RefreshToken;
import com.sm.app.web.request.TokenRefreshRequest;

public interface RefreshTokenService {
    RefreshToken findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    int deleteByUserId(Long userId);

    String validateAndGenerateRefreshToken(TokenRefreshRequest request);
}
