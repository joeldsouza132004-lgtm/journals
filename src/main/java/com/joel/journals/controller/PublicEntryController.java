package com.joel.journals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicEntryController {

    @org.springframework.beans.factory.annotation.Autowired
    private com.joel.journals.service.usersService usersService;

    @org.springframework.web.bind.annotation.PostMapping("/create-user")
    public void createUser(@org.springframework.web.bind.annotation.RequestBody com.joel.journals.entity.UserEntry entry) {
        usersService.SaveNewEntry(entry);
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
