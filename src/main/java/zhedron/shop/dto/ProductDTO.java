package zhedron.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;

    private String name;

    private String description;

    private double price;

    private Boolean isBuy;

    private String imageUrl;

    private String contentType;
}
