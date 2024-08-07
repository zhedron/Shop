package zhedron.shop.services;

import zhedron.shop.dto.ProductDTO;
import zhedron.shop.models.Product;

import java.util.List;

public interface ProductService {
    void save (Product product);

    List<ProductDTO> findAll ();

    ProductDTO findById (long id);
}
