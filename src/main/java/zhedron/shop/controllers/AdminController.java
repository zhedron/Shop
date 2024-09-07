package zhedron.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.enums.Role;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.models.User;
import zhedron.shop.services.ProductService;
import zhedron.shop.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService adminService;
    private final ProductService productService;

    public AdminController(UserService adminService, ProductService productService) {
        this.adminService = adminService;
        this.productService = productService;
    }

    @GetMapping("/users")
    public List<UserDTO> findAll () {
        return adminService.findAll();
    }

    @PostMapping("/addproduct/{productId}/{userId}")
    public String addProductToUser (@PathVariable long productId, @PathVariable long userId) throws Exception {
        adminService.addProductToUser(productId, userId);

        return "Saved Product to User";
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable long id) {
        try {
            adminService.delete(id);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping ("/update/{id}")
    public ResponseEntity<?> update (@PathVariable long id, @RequestBody User user) {
        try {
            adminService.update(id, user);

            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping ("/deleteproduct/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable long id) {
        try {
            productService.delete(id);

            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (ProductNotExistException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping ("/addbasket/{userId}/{productId}")
    public String addBasketToUser (@PathVariable long userId, @PathVariable long productId) {
        try {
            adminService.addBasketToUser(userId, productId);
            return "Add Basket";
        } catch (UserNotExistException | ProductNotExistException e) {
            System.out.println(e.getMessage());
            return "User/Product Not Exist";
        }
    }

    @PatchMapping ("/changerole/{userId}")
    public ResponseEntity<?> changeRole (@PathVariable long userId, @RequestParam Role role) throws UserNotExistException {
        System.out.println(role.toString());

        adminService.update(userId, role);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
