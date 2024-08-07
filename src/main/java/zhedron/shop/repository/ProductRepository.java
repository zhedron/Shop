package zhedron.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhedron.shop.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
