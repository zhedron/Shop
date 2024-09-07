package zhedron.shop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.models.User;
import zhedron.shop.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public String create (@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }

        service.save(user);

        return "User created";
    }

    @GetMapping("/users")
    public List<UserDTO> findAll () {
        return service.findAll();
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
}
