package zhedron.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhedron.shop.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User deleteById(long id);
}
