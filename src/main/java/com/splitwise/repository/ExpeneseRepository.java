package com.splitwise.repository;

import com.splitwise.entity.Expenses;
import com.splitwise.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExpeneseRepository  extends MongoRepository<Expenses, Long> {

    List<Expenses> findByGroupId(Long id);
}
