package zhedron.shop.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import zhedron.shop.dto.ProductDTO;
import zhedron.shop.services.ProductService;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    private final String URL = "/api";

    @Test
    public void findProducts() throws Exception {
        ProductDTO product = new ProductDTO();

        product.setName("Йогурт");
        product.setIsBuy(true);
        product.setPrice(250.00);
        product.setDescription("Продукт");

        List<ProductDTO> products = new ArrayList<>();

        products.add(product);

        when(productService.findAll()).thenReturn(products);

        mvc.perform(get(URL + "/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Йогурт"))
                .andExpect(jsonPath("$[0].isBuy").value(true))
                .andExpect(jsonPath("$[0].price").value(250.00))
                .andExpect(jsonPath("$[0].description").value("Продукт"));
    }

    @Test
    public void findProduct() throws Exception {
        ProductDTO product = new ProductDTO();

        product.setName("Йогурт");

        List<ProductDTO> products = new ArrayList<>();
        products.add(product);

        when(productService.findByName("Йогурт")).thenReturn(products);

        mvc.perform(get(URL + "/search?name=Йогурт"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Йогурт"));
    }
}
