package zhedron.shop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.exceptions.EmailExistException;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.models.User;
import zhedron.shop.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create (@Valid @RequestBody User user, BindingResult bindingResult) throws EmailExistException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }

        service.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
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
            return "User/Product Not Exist";
        }
    }
}
