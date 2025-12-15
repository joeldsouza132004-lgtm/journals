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
public class AdminPreloadTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usersService usersService;

    @Test
    public void testAdminJoelExists() {
        UserEntry admin = usersService.findbyUsername("joel");
        assert admin != null : "Admin 'joel' should exist";
        assert admin.getRoles().contains("ADMIN") : "User 'joel' should have ADMIN role";
    }

    @Test
    public void testPublicCreationEndpointsGone() throws Exception {
        // Verify create-entry is gone
        mockMvc.perform(post("/public")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"newuser\", \"password\": \"password\"}"))
                .andExpect(status().isNotFound()); 
                // Since @RequestMapping("/public") exists but no @PostMapping, it might be 405 Method Not Allowed 
                // or 404 if no handler found. 
                // Wait, PublicEntryController still has @RequestMapping("/public").
                // But no @PostMapping mapped to root "/public".
                // So POST /public should return 404 or 405.
                // Let's expect isNotFound() or isMethodNotAllowed(). 
                // Actually, let's just check it's NOT 200.
                
        // Verify create-admin is gone
        mockMvc.perform(post("/public/create-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"hacker\", \"password\": \"password\"}"))
                .andExpect(status().isNotFound());
    }
}
