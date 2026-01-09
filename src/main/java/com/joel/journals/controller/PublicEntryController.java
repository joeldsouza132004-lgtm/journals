package com.joel.journals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.joel.journals.service.sqs.SqsProducerService;

@RestController
@RequestMapping("/public")
public class PublicEntryController {

    @org.springframework.beans.factory.annotation.Autowired
    private com.joel.journals.service.usersService usersService;

    @Autowired
    private SqsProducerService sqsProducerService;

    @org.springframework.web.bind.annotation.PostMapping("/create-user")
    public void createUser(@org.springframework.web.bind.annotation.RequestBody com.joel.journals.entity.UserEntry entry) {
        usersService.SaveNewEntry(entry);
        sqsProducerService.sendMessage("USER_CREATED:"+entry.getUsername());
    }




    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
