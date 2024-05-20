package com.splitwise.repository;

import com.splitwise.entity.Members;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends MongoRepository<Members, Long> {
}
