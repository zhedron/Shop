package zhedron.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zhedron.shop.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product deleteById(long id);

    List<Product> findProductsByName (String name);
}
