package zhedron.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.models.User;
import zhedron.shop.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody User userDTO) {
        service.save(userDTO);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public List<UserDTO> findAll () {
        return service.findAll();
    }

    @PostMapping("/addproduct/{id}/{userId}")
    public String addProductToUser (@PathVariable long id, @PathVariable long userId) throws Exception {
        service.addProductToUser(id, userId);

        return "Saved Product to User";
    }

    @PostMapping ("/addbasket/{userId}/{productId}")
    public String addBasketToUser (@PathVariable long userId, @PathVariable long productId) {
        service.addBasketToUser(userId, productId);

        return "Add Basket";
    }

    @GetMapping("/{id}")
    public UserDTO findUser (@PathVariable long id) {
        return service.findById(id);
    }
}
