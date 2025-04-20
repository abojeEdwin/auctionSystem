package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByUsername(String username);
    boolean existsByEmail(String email);
    Admin findByEmail(String email);
    boolean existsByUsername(String username);
}

