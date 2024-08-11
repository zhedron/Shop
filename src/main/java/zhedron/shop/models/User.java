package zhedron.shop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zhedron.shop.enums.Role;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String surname;

    @Column
    private String password;

    @Column
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<Product> products;

    @OneToMany
    private List<Basket> baskets;
}
