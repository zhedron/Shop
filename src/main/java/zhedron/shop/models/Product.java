package zhedron.shop.models;

import jakarta.persistence.*;
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
    private String name;

    @Column
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
