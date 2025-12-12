package com.joel.journals.config;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.service.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private usersService usersService;

    @Override
    public void run(String... args) throws Exception {
        String adminUsername = "joel";
        if (usersService.findbyUsername(adminUsername) == null) {
            UserEntry admin = new UserEntry();
            admin.setUsername(adminUsername);
            admin.setPassword("jcd@123");
            usersService.saveAdmin(admin);
            System.out.println("Admin user 'joel' created successfully.");
        } else {
            System.out.println("Admin user 'joel' already exists.");
        }
    }
}
