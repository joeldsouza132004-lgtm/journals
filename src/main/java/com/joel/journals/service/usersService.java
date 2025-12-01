package com.joel.journals.service;

import com.joel.journals.entity.users;
import com.joel.journals.repositary.usersrepos;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.PasswordAuthentication;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class usersService {

    @Autowired
    private usersrepos usersrepos;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void saveEntry(users userentry) {
        userentry.setPassword(encoder.encode(userentry.getPassword()));
        userentry.setRoles(Arrays.asList("USER"));
        usersrepos.save(userentry);
    }

    public void saveNewEntry(users userentry) {
        usersrepos.save(userentry);
    }

    public List<users> getEntries() {

        return usersrepos.findAll();
    }
    public Optional<users> getEntryById(ObjectId id) {

        return usersrepos.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        usersrepos.deleteById(id);
    }
    public users findbyUsername(String username) {
        return usersrepos.findByUsername(username);
    }

}
