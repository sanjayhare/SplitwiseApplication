package com.splitwise.repository;

import com.splitwise.entity.Expenses;
import com.splitwise.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpeneseRepository  extends MongoRepository<Expenses, Long> {
}
