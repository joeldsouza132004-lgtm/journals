package com.joel.journals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/")
    public String home() {
        return "hello-from-app-engine";
    }

    @GetMapping("/app")
    public String app(){
        return "heloooongine";
    }
}
