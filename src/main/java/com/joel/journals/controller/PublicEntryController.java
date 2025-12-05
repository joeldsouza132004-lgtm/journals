package com.joel.journals.controller;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicEntryController {

    @Autowired
    private usersService userentry;

    @PostMapping
    public void createEntry(@RequestBody UserEntry entry) {
        userentry.SaveNewEntry(entry);
    }
}
