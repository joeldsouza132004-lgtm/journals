package com.joel.journals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicEntryController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
