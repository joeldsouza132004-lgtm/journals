package com.joel.journals;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private usersService usersService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testUpdateUser() throws Exception {
        UserEntry existingUser = new UserEntry();
        existingUser.setUsername("testuser");
        existingUser.setPassword("oldpass");
        
        when(usersService.findbyUsername("testuser")).thenReturn(existingUser);

        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testuser\", \"password\": \"newpass\"}"))
                .andExpect(status().isNoContent());
    }
}
