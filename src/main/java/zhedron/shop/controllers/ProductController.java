package zhedron.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhedron.shop.dto.ProductDTO;
import zhedron.shop.exceptions.ProductNotExistException;
import zhedron.shop.models.Product;
import zhedron.shop.services.ProductService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class ProductController {
    private final ProductService service;
    private final String BASE_DIRECTORY = "images/";

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

        service.save(product);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public List<ProductDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage (@PathVariable long id) throws IOException {
        try {
            ProductDTO productDTO = service.findById(id);

            if (productDTO != null) {
                File file = new File(BASE_DIRECTORY + productDTO.getImageUrl());

                byte[] image = Files.readAllBytes(file.toPath());

                MediaType mediaType = MediaType.parseMediaType(productDTO.getContentType());

                return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(mediaType).body(image);
            }
        } catch (ProductNotExistException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return null;
    }
}