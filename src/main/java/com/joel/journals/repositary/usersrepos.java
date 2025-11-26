package com.joel.journals.repositary;

import com.joel.journals.entity.users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface usersrepos extends MongoRepository<users, ObjectId> {
    users findByUsername(String username);
}
