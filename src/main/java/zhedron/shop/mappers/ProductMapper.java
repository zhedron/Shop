package zhedron.shop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import zhedron.shop.dto.ProductDTO;
import zhedron.shop.models.Product;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductDTO toDTO (Product product);

    Product toEntity (ProductDTO productDTO);

    List<ProductDTO> toDTOList (List<Product> products);
}
