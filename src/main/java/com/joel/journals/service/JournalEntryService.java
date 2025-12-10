package com.joel.journals.service;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.entity.UserEntry;
import com.joel.journals.repositary.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private usersService usersService;

    @Transactional
    public JornalEntry saveEntry(JornalEntry entry, String username) {
        // 1. Find user
        UserEntry user = usersService.findbyUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        // 2. Set metadata on entry (if needed)
        entry.setDate(LocalDateTime.now());
        // If you have a userId field in JornalEntry, set it:
        // entry.setUserId(user.getId());

        // 3. Save the entry
        JornalEntry saved = journalEntryRepo.save(entry);

        // 4. Attach to user's list and save user
        if (user.getJornalEntries() == null) {
            user.setJornalEntries(new ArrayList<>());
        }
        user.getJornalEntries().add(saved);

        usersService.SaveNewEntry(user);   // make sure usersService has this

        return saved;
    }

    public void saveEntry(JornalEntry myentry) {
        journalEntryRepo.save(myentry);
    }

    public List<JornalEntry> getEntries() {
        return   journalEntryRepo.findAll();
    }
    public Optional<JornalEntry> getEntryById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId id, String username) {
        boolean removed=false;
        try {
            UserEntry user = usersService.findbyUsername(username);
            removed = user.getJornalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                usersService.saveEntry(user);
                journalEntryRepo.deleteById(id);
            }
        }catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException(("an error while deleting the entry . . . . . "));
        }
        return removed;
    }


}
