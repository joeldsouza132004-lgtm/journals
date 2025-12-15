package com.joel.journals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private com.joel.journals.service.usersService usersService;

    @Test
    public void testPublicPingEndpoint() throws Exception {
        mockMvc.perform(get("/public/ping"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPublicCreateEntryEndpoint() throws Exception {
        mockMvc.perform(post("/public/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\", \"password\": \"test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPublicEndpointWithInvalidAuth() throws Exception {
        mockMvc.perform(get("/public/ping")
                .header("Authorization", "Basic invalid"))
                .andExpect(status().isUnauthorized());
    }
}
