package zhedron.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhedron.shop.models.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
}
