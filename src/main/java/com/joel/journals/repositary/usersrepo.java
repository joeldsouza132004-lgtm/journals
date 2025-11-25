package com.joel.journals.repositary;

import com.joel.journals.entity.users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface usersrepo extends MongoRepository<users, ObjectId> {
}
