package com.joel.journals.controller;

import com.joel.journals.entity.JornalEntry;
import com.joel.journals.entity.users;
import com.joel.journals.service.JournalEntryService;
import com.joel.journals.service.usersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public users createEntry(@RequestBody users entry) {

        userentry.saveEntry(entry);
        return entry;
    }

    @GetMapping("/id/{myid}")
    public users getByid(@PathVariable ObjectId myid){
        return userentry.getEntryById(myid).orElse(null);
    }

    @DeleteMapping("/{myid}")
    public boolean deleteEntry(@PathVariable ObjectId myid){
        userentry.deleteEntryById(myid);
        return true;
    }

//    @PutMapping("/{id}")
//    public users upadteEntry(@PathVariable ObjectId id,@RequestBody JornalEntry newentry){
//        users old=userentry.getEntryById(id).orElse(null);
//        if(old!=null){
//            old.setTitle(newentry.getTitle()!=null && newentry.getTitle().equals("")?newentry.getTitle():old.getTitle());
//            old.setContent(newentry.getContent()!=null && newentry.getContent().equals("")?newentry.getContent():old.getContent());
//        }
//        userentry.saveEntry(old);
//
//        return old;
//
//    }

}
