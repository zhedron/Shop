package zhedron.shop.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zhedron.shop.models.Basket;
import zhedron.shop.repository.BasketRepository;
import zhedron.shop.services.BasketService;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository repository;


    @Override
    public void save(Basket basket) {
       repository.save(basket);
    }

    @Override
    public Basket findById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Basket> findAll() {
        return repository.findAll();
    }
}
