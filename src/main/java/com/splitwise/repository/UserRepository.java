package com.splitwise.repository;



import com.splitwise.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users,String> {

    Optional<Users> findByMobileNumberOrEmailId(String mobileNumber, String email);
    Optional<Users> findByMobileNumber(String mobileNumber);
    Optional<Users>findByEmailId(String email);


}
