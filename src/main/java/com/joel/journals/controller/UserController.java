package com.joel.journals.controller;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private usersService userentry;



    @GetMapping
    public List<UserEntry> getAll() {
        return userentry.getEntries();
    }

    @GetMapping("/id/{myid}")
    public UserEntry getByid(@PathVariable ObjectId myid){
        return userentry.getEntryById(myid).orElse(null);
    }

//    @DeleteMapping("/{myid}")
//    public boolean deleteEntry(@PathVariable ObjectId myid){
//        userentry.deleteEntryById(myid);
//        return true;
//    }

    @PutMapping()
    public ResponseEntity<?> updateuser(@RequestBody UserEntry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry use= userentry.findbyUsername(username);
        if(use!=null){
            use.setUsername(entry.getUsername());
            use.setPassword(entry.getPassword());
            userentry.SaveNewEntry(use);
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
