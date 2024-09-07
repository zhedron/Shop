package zhedron.shop.services;

import zhedron.shop.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(String email);

    RefreshToken verifyExpiration (RefreshToken refreshToken);

    Optional<RefreshToken> findByToken (String token);
}
