package com.joel.journals.service;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.repositary.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    public void saveEntry(JornalEntry myentry) {
        journalEntryRepo.save(myentry);
    }

    public List<JornalEntry> getEntries() {
        return   journalEntryRepo.findAll();
    }
}
