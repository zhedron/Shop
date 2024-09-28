package zhedron.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhedron.shop.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
