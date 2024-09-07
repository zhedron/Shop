package zhedron.shop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zhedron.shop.enums.Role;
import zhedron.shop.models.Basket;
import zhedron.shop.models.Product;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;

    private String name;

    private String email;

    private String surname;

    private double balance;

    private Role role;

    private List<Product> products;

    private List<Basket> baskets;

    @JsonFormat (pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdAt;
}
