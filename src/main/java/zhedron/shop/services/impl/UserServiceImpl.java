package zhedron.shop.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zhedron.shop.dto.ProductDTO;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.enums.Role;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.exceptions.UserBalanceException;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.mappers.ProductMapper;
import zhedron.shop.mappers.UserMapper;
import zhedron.shop.models.Basket;
import zhedron.shop.models.Product;
import zhedron.shop.models.User;
import zhedron.shop.repository.UserRepository;
import zhedron.shop.services.BasketService;
import zhedron.shop.services.ProductService;
import zhedron.shop.services.UserService;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final ProductMapper productMapper;
    private final ProductService service;
    private final BasketService basketService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);

        log.info("Created user: {}", user);
    }

    @Override
    public void delete(long id) throws UserNotExistException {
        User user = repository.deleteById(id);

        if (user == null) {
            throw new UserNotExistException("User not found with id: " + id);
        }
    }

    @Override
    public List<UserDTO> findAll () {
        List<User> users = repository.findAll();

        return mapper.toDTOList(users);
    }

    @Override
    public UserDTO findById (long id) throws UserNotExistException {
        User user = repository.findById(id).orElseThrow(() -> new UserNotExistException("User not found with id: " + id));

        return mapper.toDTO(user);
    }

    @Override
    public void addProductToUser (long id, long userId) throws UserNotExistException, UserBalanceException, ProductNotExistException {
        ProductDTO productDTO = service.findById(id);

        Product product = productMapper.toEntity(productDTO);

        UserDTO userDTO = findById(id);

        User user = mapper.toEntity(userDTO);

        if (product != null && user != null) {
            if (user.getBalance() < product.getPrice()) {
                throw new UserBalanceException("Not enough balance to add product");
            }

            double price = product.getPrice();
            double balance = user.getBalance() - price;

            user.setBalance(balance);

            product.setIsBuy(true);
            service.save(product);

            user.getProducts().add(product);
            repository.save(user);

            log.info("Saved User: {}", user);
        }
    }

    @Override
    public void addBasketToUser (long id, long productId) throws UserNotExistException, ProductNotExistException {
        ProductDTO productDTO = service.findById(productId);

        Product product = productMapper.toEntity(productDTO);

        UserDTO userDTO = findById(id);

        User user = mapper.toEntity(userDTO);

        if (user != null && productDTO != null) {
            Basket basket = new Basket();

            basket.setProduct(product);

            basketService.save(basket);

            user.getBaskets().add(basket);

            repository.save(user);


            log.info("Saved: {}, {}", basket, user);
        }
    }

    @Override
    public void update (long id, User updatedUser) throws UserNotExistException {
        User user = repository.findById(id).orElseThrow(() -> new UserNotExistException("User not found with id: " + id));

        if (user != null) {
            user.setName(updatedUser.getName());
            user.setSurname(updatedUser.getSurname());
            user.setPassword(updatedUser.getPassword());

            repository.save(user);
        }
    }
}
