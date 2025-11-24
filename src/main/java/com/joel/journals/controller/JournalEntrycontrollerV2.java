package com.joel.journals.controller;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.service.JournalEntryService;
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
    public boolean createEntry(@RequestBody JornalEntry myentry) {
        myentry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry( myentry );
        return true;
    }

    @GetMapping("/id/{myid}")
    public JornalEntry getByid(@PathVariable Long myid){
        return null;
    }

    @DeleteMapping("/{myid}")
    public boolean deleteEntry(@PathVariable Long myid){
        return true;
    }

    @PutMapping("/{id}")
    public JornalEntry upadteEntry(@PathVariable Long id,@RequestBody JornalEntry myentry){
        return null;

    }

}
