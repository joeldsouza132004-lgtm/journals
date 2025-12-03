package com.joel.journals.controller;

import com.joel.journals.entity.users;
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
        List<users> entries = usersService.getEntries();
        if(entries!=null && !entries.isEmpty()){
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> craeteAdmin(@RequestBody users user){
        usersService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
