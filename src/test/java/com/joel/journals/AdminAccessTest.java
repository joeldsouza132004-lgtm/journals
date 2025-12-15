package com.joel.journals;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usersService usersService;

    @Test
    public void testAdminAccessControl() throws Exception {
        String userPass = "password";
        String adminPass = "password";
        

        String regularUser = "regUser_" + System.currentTimeMillis();
        UserEntry user = new UserEntry();
        user.setUsername(regularUser);
        user.setPassword(userPass);
        usersService.SaveNewEntry(user);

        String adminUser = "adminUser_" + System.currentTimeMillis();
        UserEntry admin = new UserEntry();
        admin.setUsername(adminUser);
        admin.setPassword(adminPass);
        usersService.saveAdmin(admin);


        mockMvc.perform(get("/admin/all-journals")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((regularUser + ":" + userPass).getBytes())))
                .andExpect(status().isForbidden());


        mockMvc.perform(get("/admin/all-journals")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((adminUser + ":" + adminPass).getBytes())))
                .andExpect(status().isOk());

        //  Verify Regularelete journal entry (using a fake ID) User CANNOT d
        mockMvc.perform(delete("/journal/507f1f77bcf86cd799439011")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((regularUser + ":" + userPass).getBytes())))
                .andExpect(status().isForbidden());

        //  Verify Admin User CAN delete journal entry (using a fake ID)
        mockMvc.perform(delete("/journal/507f1f77bcf86cd799439011")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((adminUser + ":" + adminPass).getBytes())))
                .andExpect(status().isNotFound()); // NotFound means it passed security check
    }
}
