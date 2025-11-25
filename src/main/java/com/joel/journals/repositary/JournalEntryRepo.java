package com.joel.journals.repositary;

import com.joel.journals.entity.JornalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JornalEntry, ObjectId> {
}
