package zhedron.shop.services;

import zhedron.shop.dto.UserDTO;
import zhedron.shop.enums.Role;
import zhedron.shop.exceptions.EmailExistException;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.exceptions.UserBalanceException;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.models.User;

import java.util.List;


public interface UserService {
    User save (User user) throws EmailExistException;

    void delete (long id) throws UserNotExistException;

    List<User> findAll ();

    UserDTO findById (long id) throws UserNotExistException;

    void update (long id, User user) throws UserNotExistException;

    User findByEmail (String email) throws UserNotExistException;

    void addProductToUser (long productId, long userId) throws UserNotExistException, UserBalanceException, ProductNotExistException;

    void addBasketToUser (long userId, long productId) throws UserNotExistException, ProductNotExistException;

    void update (long userId, Role role) throws UserNotExistException;
}
