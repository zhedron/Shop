package zhedron.shop.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zhedron.shop.models.Basket;
import zhedron.shop.repository.BasketRepository;
import zhedron.shop.services.BasketService;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository repository;

    @Override
    public void save(Basket basket) {
       repository.save(basket);
    }
}
