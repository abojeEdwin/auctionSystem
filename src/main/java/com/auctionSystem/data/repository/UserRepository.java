package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
