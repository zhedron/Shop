package zhedron.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhedron.shop.enums.Role;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.models.Product;
import zhedron.shop.models.User;
import zhedron.shop.services.ProductService;
import zhedron.shop.services.UserService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final String BASE_DIRECTORY = "images/";

    private final UserService adminService;
    private final ProductService productService;

    public AdminController(UserService adminService, ProductService productService) {
        this.adminService = adminService;
        this.productService = productService;
    }

    @GetMapping("/users")
    public List<User> findAll () {
        return adminService.findAll();
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

    @PatchMapping ("/changerole/{userId}")
    public ResponseEntity<?> changeRole (@PathVariable long userId, @RequestParam Role role) throws UserNotExistException {
        System.out.println(role.toString());

        adminService.update(userId, role);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping("/createproduct")
    public ResponseEntity<?> create(@RequestPart Product product, @RequestPart MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());

        File dir = new File(BASE_DIRECTORY);

        if (!dir.exists()) {
            dir.mkdir();
        }

        String url = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        String path = BASE_DIRECTORY + url;

        File image = new File(path);

        file.transferTo(image.toPath());

        product.setImageUrl(url);
        product.setContentType(file.getContentType());

        productService.save(product);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
