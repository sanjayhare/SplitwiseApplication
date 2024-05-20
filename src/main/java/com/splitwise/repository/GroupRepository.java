package com.splitwise.repository;

import com.splitwise.entity.Group;
import com.splitwise.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, Long> {

    Optional<Group> findByTitle(String title);
}
