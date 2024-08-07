package zhedron.shop.services;

import zhedron.shop.models.Basket;

import java.util.List;

public interface BasketService {
    void save (Basket basket);

    Basket findById (long id);

    List<Basket> findAll ();
}
