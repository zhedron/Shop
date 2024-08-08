package zhedron.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.exceptions.UserNotExistException;
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

    @PostMapping("/addproduct/{productId}/{userId}")
    public String addProductToUser (@PathVariable long productId, @PathVariable long userId) throws Exception {
        service.addProductToUser(productId, userId);

        return "Saved Product to User";
    }

    @PostMapping ("/addbasket/{userId}/{productId}")
    public String addBasketToUser (@PathVariable long userId, @PathVariable long productId) {
        try {
            service.addBasketToUser(userId, productId);
            return "Add Basket";
        } catch (UserNotExistException | ProductNotExistException e) {
            System.out.println(e.getMessage());
            return "User Not Exist";
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> findUser (@PathVariable long id) {
        try {
            UserDTO userDTO = service.findById(id);

            return ResponseEntity.ok(userDTO);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> delete (@PathVariable long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<?> update (@PathVariable long id, @RequestBody User user) {
        try {
            service.update(id, user);

            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
