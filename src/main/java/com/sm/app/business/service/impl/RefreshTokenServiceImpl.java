package com.sm.app.business.service.impl;

import com.sm.app.business.service.RefreshTokenService;
import com.sm.app.config.securtiy.JwtUtility;
import com.sm.app.config.securtiy.SecurityConstants;
import com.sm.app.exception.TokenRefreshException;
import com.sm.app.orm.entity.RefreshToken;
import com.sm.app.orm.repository.RefreshTokenRepository;
import com.sm.app.orm.repository.UserRepository;
import com.sm.app.web.request.TokenRefreshRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;


@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtility jwtUtility;

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(SecurityConstants.REFRESH_EXPIRATION_TIME));// plus mills
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    @Override
    public String validateAndGenerateRefreshToken(TokenRefreshRequest request) {
        RefreshToken refreshToken = findByToken(request.getRefreshToken());
        if (refreshToken != null) {
            return jwtUtility.generateTokenByUserName(verifyExpiration(refreshToken).getUser().getEmail());
        }
        throw new TokenRefreshException(request.getRefreshToken(), "Refresh token is invalid!");
    }
}
