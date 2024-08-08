package zhedron.shop.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zhedron.shop.dto.ProductDTO;
import zhedron.shop.exceptions.ProductNotExistException;
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
        repository.save(product);
        log.info("Created product: {}", product);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> users = repository.findAll();

        return mapper.toDTOList(users);
    }

    @Override
    public ProductDTO findById (long id) throws ProductNotExistException {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotExistException ("Not found product with id " + id));

        return mapper.toDTO(product);
    }

    @Override
    public void delete (long id) throws ProductNotExistException {
        Product product = repository.deleteById(id);

        if (product == null) {
            throw new ProductNotExistException ("Not found product with id " + id);
        }
    }
}
