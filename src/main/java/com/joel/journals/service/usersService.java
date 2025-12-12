package com.joel.journals.service;

import com.joel.journals.entity.UserEntry;
import com.joel.journals.repositary.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class usersService {

    @Autowired
    private UserEntryRepo usersrepos;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void SaveNewEntry(UserEntry userentry) {
        userentry.setPassword(encoder.encode(userentry.getPassword()));
        userentry.setRoles(Arrays.asList("USER"));
        usersrepos.save(userentry);
    }

    public void saveAdmin(UserEntry userentry) {
        userentry.setPassword(encoder.encode(userentry.getPassword()));
        userentry.setRoles(Arrays.asList("USER","ADMIN"));
        usersrepos.save(userentry);
    }

    public void saveEntry(UserEntry userentry) {
        usersrepos.save(userentry);
    }

    public void saveUser(UserEntry user) {
        usersrepos.save(user);
    }

    public List<UserEntry> getEntries() {

        return usersrepos.findAll();
    }
    public Optional<UserEntry> getEntryById(ObjectId id) {

        return usersrepos.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        usersrepos.deleteById(id);
    }
    public UserEntry findbyUsername(String username) {
        return usersrepos.findByUsername(username);
    }

}
