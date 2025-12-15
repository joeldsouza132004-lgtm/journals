package com.joel.journals;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.junit.jupiter.api.BeforeEach;
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
public class JournalEntryFlowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usersService usersService;

    @BeforeEach
    public void setup() {
        // Ensure clean state if possible, or use unique usernames
    }

    @Test
    public void testCreateJournalEntryCorruptsPassword() throws Exception {
        String username = "flowtestuser_" + System.currentTimeMillis();
        String password = "password123";

        // 1. Create User
        UserEntry user = new UserEntry();
        user.setUsername(username);
        user.setPassword(password);
        usersService.SaveNewEntry(user);

        // 2. Create First Journal Entry (Should succeed)
        mockMvc.perform(post("/journal")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"First Entry\", \"content\": \"Content 1\"}"))
                .andExpect(status().isOk());

        // 3. Create Second Journal Entry (Should fail if password was corrupted)
        mockMvc.perform(post("/journal")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Second Entry\", \"content\": \"Content 2\"}"))
                .andExpect(status().isOk()); // Expect OK, but will fail with 401 if bug exists
    }

    @Autowired
    private com.joel.journals.service.JournalEntryService journalEntryService;

    @Test
    public void testGetAllJournalEntries() throws Exception {
        String username = "gettestuser_" + System.currentTimeMillis();
        String password = "password123";

        // 1. Create User
        UserEntry user = new UserEntry();
        user.setUsername(username);
        user.setPassword(password);
        usersService.SaveNewEntry(user);

        // 2. Create Entry
        mockMvc.perform(post("/journal")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Entry 1\", \"content\": \"Content 1\"}"))
                .andExpect(status().isOk());


        // 3. Get All Entries
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/journal")
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetJournalEntryById() throws Exception {
        String username = "idtestuser_" + System.currentTimeMillis();
        String password = "password123";

        // 1. Create User
        UserEntry user = new UserEntry();
        user.setUsername(username);
        user.setPassword(password);
        usersService.SaveNewEntry(user);

        // 2. Create Entry manually to get ID easily, or just use API and fetch all to get ID
        com.joel.journals.entity.JornalEntry entry = new com.joel.journals.entity.JornalEntry();
        entry.setTitle("Specific Entry");
        entry.setContent("Specific Content");
        entry.setDate(java.time.LocalDateTime.now());
        journalEntryService.saveEntry(entry, username);

        // 3. Get Entry by ID
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/journal/id/" + entry.getId())
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes())))
                .andExpect(status().isOk());
    }
}
