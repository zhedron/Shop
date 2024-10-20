package zhedron.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zhedron.shop.dto.ProductDTO;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.enums.Role;
import zhedron.shop.models.Basket;
import zhedron.shop.models.Product;
import zhedron.shop.models.User;
import zhedron.shop.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String URL = "/api";

    public String getToken () {
        return "Bearer " + "<token>";
    }

    @Test
    public void createUser () throws Exception {
        User user = new User();

        user.setId(1L);
        user.setName("Zheka");
        user.setSurname("Dronov");
        user.setEmail("test4@gmail.com");
        user.setPassword("1234");

        when(userService.save(any(User.class))).thenReturn(user);

        mvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getSurname());
    }

    @Test
    public void cannotCreateUser () throws Exception {
        User user = new User();

        user.setId(1L);
        user.setName("");
        user.setSurname("");
        user.setEmail("test4");

        when(userService.save(any(User.class))).thenReturn(user);

        mvc.perform(post(URL + "/create").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserById () throws Exception {
        UserDTO user = new UserDTO();

        user.setId(1L);
        user.setName("test2");
        user.setSurname("meowlove7");
        user.setBalance(250.00);
        user.setEmail("test4@gmail.com");

        when(userService.findById(1L)).thenReturn(user);

        mvc.perform(get(URL + "/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test2"))
                .andExpect(jsonPath("$.email").value("test4@gmail.com"));

        assertEquals("test2", user.getName());
        assertEquals(250.00, user.getBalance());
        assertEquals("test4@gmail.com", user.getEmail());
    }

    @Test
    public void getUsers () throws Exception {
        when(userService.findAll()).thenReturn(List.of(new User(1L, "zhedron", "test4@gmail.com", "Dronov", "test", 250.00, Role.ROLE_ADMIN, new ArrayList<>(), new ArrayList<>(), LocalDateTime.now())));

        mvc.perform(get(URL + "/admin/users")
                        .header("Authorization", getToken())).andExpect(status().isOk());
    }

    @Test
    public void addProductToUser () throws Exception {
        ProductDTO product = new ProductDTO();

        UserDTO user = new UserDTO();

        product.setId(1L);
        product.setName("Йогурт");
        product.setDescription("Продукт");
        product.setPrice(250.00);
        product.setIsBuy(true);

        List<ProductDTO> products = new ArrayList<>();
        products.add(product);

        user.setId(1L);
        user.setProducts(products);

        Mockito.doNothing().when(userService).addProductToUser(product.getId(), user.getId());

        mvc.perform(post(URL + "/addproduct/{productId}/{userId}", 1L, 1L)).andExpect(status().isOk());

        assertTrue(product.getIsBuy());
    }
    @Test
    public void addBasketToUser () throws Exception {
        Product product = new Product();

        UserDTO user = new UserDTO();

        Basket basket = new Basket();

        product.setId(1L);
        product.setName("Йогурт");
        product.setDescription("Продукт");
        product.setPrice(250.00);
        product.setIsBuy(true);

        basket.setId(1L);
        basket.setProduct(product);

        List<Basket> baskets = new ArrayList<>();
        baskets.add(basket);

        user.setId(1L);
        user.setBaskets(baskets);

        Mockito.doNothing().when(userService).addBasketToUser(user.getId(), basket.getId());

        mvc.perform(post(URL + "/addbasket/{userId}/{productId}", 1L, 1L)).andExpect(status().isOk());

        assertNotNull(product.getName());
        assertNotNull(product.getDescription());
    }
}
