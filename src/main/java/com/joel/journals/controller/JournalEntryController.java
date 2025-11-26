package com.joel.journals.controller;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.entity.users;
import com.joel.journals.service.JournalEntryService;
import com.joel.journals.service.usersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")

public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private usersService usersService;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        users user=usersService.findbyUsername(username);
        List<JornalEntry> all =user.getJornalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<JornalEntry> createEntry(@RequestBody JornalEntry myentry,@PathVariable String username) {
        try{
            journalEntryService.saveEntry( myentry,username);
            myentry.setDate(LocalDateTime.now());
            return new ResponseEntity<>(myentry, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJournalByid(@PathVariable ObjectId myid){
        Optional<JornalEntry> jornalEntry=journalEntryService.getEntryById(myid);
        if(jornalEntry.isPresent()){
            return new ResponseEntity<>(jornalEntry.get(), HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{myid}/{username}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid,@PathVariable String username) {
        journalEntryService.deleteEntryById(myid,username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/{username}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id,@PathVariable String username,@RequestBody JornalEntry newentry){
        JornalEntry old=journalEntryService.getEntryById(id).orElse(null);
        if(old!=null){
            old.setTitle(newentry.getTitle()!=null && newentry.getTitle().equals("")?newentry.getTitle():old.getTitle());
            old.setContent(newentry.getContent()!=null && newentry.getContent().equals("")?newentry.getContent():old.getContent());
            journalEntryService.saveEntry(old);
            return  new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);



    }


}







