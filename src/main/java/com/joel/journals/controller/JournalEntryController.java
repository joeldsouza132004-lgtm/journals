package com.joel.journals.controller;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.JournalEntryService;
import com.joel.journals.service.usersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")

public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private usersService usersService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user = usersService.findbyUsername(username);
        List<JornalEntry> all =journalEntryService.getEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JornalEntry> createEntry(@RequestBody JornalEntry myentry) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry( myentry,username);
            myentry.setDate(LocalDateTime.now());
            return new ResponseEntity<>(myentry, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJournalByid(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry users = usersService.findbyUsername(username);
        List<JornalEntry> collect = users.getJornalEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JornalEntry> jornalEntry=journalEntryService.getEntryById(myid);
            return new ResponseEntity<>(jornalEntry.get(), HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{myid}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed =journalEntryService.deleteEntryById(myid,username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id,@RequestBody JornalEntry newentry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry users = usersService.findbyUsername(username);
        List<JornalEntry> collect = users.getJornalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JornalEntry> jornalEntry = journalEntryService.getEntryById(id);
            if (jornalEntry.isPresent()) {
                JornalEntry old = jornalEntry.get();
                old.setTitle(newentry.getTitle() != null && !newentry.getTitle().equals("") ? newentry.getTitle() : old.getTitle());
                old.setContent(newentry.getContent() != null && !newentry.getContent().equals("") ? newentry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}







