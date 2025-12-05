package com.joel.journals.repositary;

import com.joel.journals.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepo extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUsername(String username);
}
