package com.joel.journals.service;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.entity.users;
import com.joel.journals.repositary.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private usersService usersService;

    @Transactional
    public void saveEntry(JornalEntry myentry, String username) {
        users user=usersService.findbyUsername(username);
        JornalEntry saved =journalEntryRepo.save(myentry);
        user.getJornalEntries().add(saved);
        usersService.saveEntry(user);
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
            users user = usersService.findbyUsername(username);
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
