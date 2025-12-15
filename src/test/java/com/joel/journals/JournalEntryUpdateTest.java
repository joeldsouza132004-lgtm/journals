package com.joel.journals;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.JournalEntryService;
import com.joel.journals.service.usersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JournalEntryUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usersService usersService;

    @Autowired
    private JournalEntryService journalEntryService;

    @Test
    public void testUpdateJournalEntry() throws Exception {
        String username = "updatetestuser_" + System.currentTimeMillis();
        String password = "password123";

        // 1. Create User
        UserEntry user = new UserEntry();
        user.setUsername(username);
        user.setPassword(password);
        usersService.SaveNewEntry(user);

        // 2. Create Journal Entry
        JornalEntry entry = new JornalEntry();
        entry.setTitle("Original Title");
        entry.setContent("Original Content");
        entry.setDate(LocalDateTime.now());
        JornalEntry savedEntry = journalEntryService.saveEntry(entry, username);

        // 3. Update Journal Entry
        String newTitle = "Updated Title";
        String newContent = "Updated Content";

        mockMvc.perform(put("/journal/" + savedEntry.getId())
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"" + newTitle + "\", \"content\": \"" + newContent + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newTitle))
                .andExpect(jsonPath("$.content").value(newContent));
    }
}
