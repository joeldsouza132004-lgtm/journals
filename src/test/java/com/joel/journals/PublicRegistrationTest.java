package com.joel.journals;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PublicRegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usersService usersService;

    @Test
    public void testPublicUserRegistration() throws Exception {
        String username = "publicUser_" + System.currentTimeMillis();
        String password = "password123";

        // 1. Register User Publicly
        mockMvc.perform(post("/public/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk());

        // 2. Verify User Exists and Roles
        UserEntry user = usersService.findbyUsername(username);
        assert user != null : "User should exist";
        assert user.getRoles().contains("USER") : "User should have USER role";
        assert !user.getRoles().contains("ADMIN") : "User should NOT have ADMIN role";
    }
}
