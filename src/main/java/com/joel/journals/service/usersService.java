package com.joel.journals.service;

import com.joel.journals.entity.users;
import com.joel.journals.repositary.usersrepos;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class usersService {

    @Autowired
    private usersrepos usersrepos;

    public void saveEntry(users userentry) {
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
