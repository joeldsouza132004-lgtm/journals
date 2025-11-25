package com.joel.journals.controller;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntrycontrollerV2 {
    @Autowired
    private JournalEntryService journalEntryService;



    @GetMapping
    public List<JornalEntry> getAll() {
        return journalEntryService.getEntries();
    }

    @PostMapping
    public JornalEntry createEntry(@RequestBody JornalEntry myentry) {
        myentry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry( myentry );
        return myentry;
    }

    @GetMapping("/id/{myid}")
    public JornalEntry getByid(@PathVariable ObjectId myid){
        return journalEntryService.getEntryById(myid).orElse(null);
    }

    @DeleteMapping("/{myid}")
    public boolean deleteEntry(@PathVariable ObjectId myid){
        journalEntryService.deleteEntryById(myid);
        return true;
    }

    @PutMapping("/{id}")
    public JornalEntry upadteEntry(@PathVariable ObjectId id,@RequestBody JornalEntry newentry){
        JornalEntry old=journalEntryService.getEntryById(id).orElse(null);
        if(old!=null){
            old.setTitle(newentry.getTitle()!=null && newentry.getTitle().equals("")?newentry.getTitle():old.getTitle());
            old.setContent(newentry.getContent()!=null && newentry.getContent().equals("")?newentry.getContent():old.getContent());
        }
        journalEntryService.saveEntry(old);

        return old;

    }

}
