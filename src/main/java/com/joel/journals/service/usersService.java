package com.joel.journals.service;

import com.joel.journals.entity.users;
import com.joel.journals.repositary.usersrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class usersService {

    @Autowired
    private usersrepo usersrepo;

    public void saveEntry(users userentry) {
        usersrepo.save(userentry);
    }

    public List<users> getEntries() {

        return usersrepo.findAll();
    }
    public Optional<users> getEntryById(ObjectId id) {

        return usersrepo.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        usersrepo.deleteById(id);
    }
}
