package zhedron.shop.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zhedron.shop.dto.ProductDTO;
import zhedron.shop.mappers.ProductMapper;
import zhedron.shop.models.Product;
import zhedron.shop.repository.ProductRepository;
import zhedron.shop.services.ProductService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public void save (Product product) {
        /*User user = service.findById(id);

        if (user != null) {
            repository.save(product);
       *//*     user.getProducts().add(product);
            service.save(user);*//*


        }*/

        repository.save(product);
        log.info("Created product: {}", product);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> users = repository.findAll();

        return mapper.toDTOList(users);
    }

    @Override
    public ProductDTO findById (long id) {
        Product product = repository.findById(id).orElse(null);

        return mapper.toDTO(product);
    }
}
