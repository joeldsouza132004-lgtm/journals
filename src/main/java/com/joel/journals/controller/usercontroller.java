package com.joel.journals.controller;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.entity.users;
import com.joel.journals.service.JournalEntryService;
import com.joel.journals.service.usersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class usercontroller {

    @Autowired
    private usersService userentry;



    @GetMapping
    public List<users> getAll() {
        return userentry.getEntries();
    }

    @PostMapping
    public void createEntry(@RequestBody users entry) {

        userentry.saveEntry(entry);
    }

    @GetMapping("/id/{myid}")
    public users getByid(@PathVariable ObjectId myid){
        return userentry.getEntryById(myid).orElse(null);
    }

//    @DeleteMapping("/{myid}")
//    public boolean deleteEntry(@PathVariable ObjectId myid){
//        userentry.deleteEntryById(myid);
//        return true;
//    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateuser(@RequestBody users entry,@PathVariable String username) {
        users use= userentry.findbyUsername(username);
        if(use!=null){
            use.setUsername(entry.getUsername());
            use.setPassword(entry.getPassword());
            userentry.saveEntry(use);
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
