package zhedron.shop.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zhedron.shop.enums.Role;

import java.time.LocalDateTime;
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
    @NotBlank (message = "Name must not be empty")
    private String name;

    @Column
    @Email (message = "Indicate your email")
    @NotBlank (message = "Email must not be empty")
    private String email;

    @Column
    @NotBlank (message = "Surname must not be empty")
    private String surname;

    @Column
    @NotNull (message = "Password must not be null")
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
    @JoinColumn
    private List<Basket> baskets;

    @Column
    @JsonFormat (pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdAt;

    {
        this.createdAt = LocalDateTime.now();
    }
}
