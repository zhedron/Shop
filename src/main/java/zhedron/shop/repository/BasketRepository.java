package zhedron.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhedron.shop.models.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
