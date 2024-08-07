package zhedron.shop.services;

import zhedron.shop.dto.UserDTO;
import zhedron.shop.models.User;

import java.util.List;


public interface UserService {
    void save (User userDTO);

    void delete (long id);

    List<UserDTO> findAll ();

    UserDTO findById (long id);

    void addProductToUser (long id, long userId) throws Exception;

    void addBasketToUser (long id, long productId);
}
