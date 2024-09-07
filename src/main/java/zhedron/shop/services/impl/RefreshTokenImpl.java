package zhedron.shop.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zhedron.shop.models.RefreshToken;
import zhedron.shop.repository.RefreshTokenRepository;
import zhedron.shop.repository.UserRepository;
import zhedron.shop.services.RefreshTokenService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public RefreshToken generateRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findByEmail(email).get());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpires(new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)));
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpires().before(new Date())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + " refresh token was expired.");
        }

        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
