package zhedron.shop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    @NotBlank (message = "Name must not be empty")
    @Size (min = 5, max = 30, message = "Name should be min 5 words or max 30 words")
    private String name;

    @Column
    @NotBlank (message = "Description must not be empty")
    @Size (min = 10, max = 50, message = "Description should be min 10 words or max 50 words")
    private String description;

    @Column
    private double price;

    @Column
    private Boolean isBuy;

    @Column
    private String imageUrl;

    @Column
    private String contentType;
}
