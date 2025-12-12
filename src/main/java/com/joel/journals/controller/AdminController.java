package com.joel.journals.controller;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private usersService usersService;
    @GetMapping
    public ResponseEntity<?> getallUsers(){
        List<UserEntry> entries = usersService.getEntries();
        if(entries!=null && !entries.isEmpty()){
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> craeteAdmin(@RequestBody UserEntry user){
        usersService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Autowired
    private com.joel.journals.service.JournalEntryService journalEntryService;

    @GetMapping("/all-journals")
    public ResponseEntity<?> getAllJournalEntries() {
        List<com.joel.journals.entity.JornalEntry> all = journalEntryService.getEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/promote/{username}")
    public ResponseEntity<?> promoteUser(@PathVariable String username) {
        usersService.promoteUserToAdmin(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        usersService.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
