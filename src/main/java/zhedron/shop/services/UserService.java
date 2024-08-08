package zhedron.shop.services;

import zhedron.shop.dto.UserDTO;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.exceptions.UserBalanceException;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.models.User;

import java.util.List;


public interface UserService {
    void save (User userDTO);

    void delete (long id) throws UserNotExistException;

    List<UserDTO> findAll ();

    UserDTO findById (long id) throws UserNotExistException;

    void update (long id, User user) throws UserNotExistException;

    void addProductToUser (long id, long userId) throws UserNotExistException, UserBalanceException, ProductNotExistException;

    void addBasketToUser (long id, long productId) throws UserNotExistException, ProductNotExistException;
}
