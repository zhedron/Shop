package zhedron.shop.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import zhedron.shop.enums.Role;
import zhedron.shop.models.User;
import zhedron.shop.services.ProductService;
import zhedron.shop.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    private final String URL = "/api/admin";

    public String getToken () {
        return "Bearer " + "<token>";
    }

    @Test
    public void findAll () throws Exception {
        User user = new User(1L,
                "test4",
                "test4@gmail.com",
                "Dron",
                "test123",
                250.00,
                Role.ROLE_ADMIN,
                new ArrayList<>(),
                new ArrayList<>(),
                LocalDateTime.now());

        User user1 = new User(2L,
                "Marie",
                "Marie86@gmail.com",
                "Avgeropoulos",
                "test123",
                300.00,
                Role.ROLE_USER,
                new ArrayList<>(),
                new ArrayList<>(),
                LocalDateTime.now());

        List<User> users = new ArrayList<>();

        users.add(user);
        users.add(user1);

        when(userService.findAll()).thenReturn(users);

        mvc.perform(get(URL + "/users")
                        .header("Authorization", getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserById () throws Exception {
        Mockito.doNothing().when(userService).delete(1L);

        mvc.perform(delete(URL + "/deleteuser/{id}", 1)
                        .header("Authorization", getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductById () throws Exception {
        Mockito.doNothing().when(productService).delete(1L);

        mvc.perform(delete(URL + "/deleteproduct/{id}", 1)
                .header("Authorization", getToken())).andExpect(status().isOk());
    }
}
