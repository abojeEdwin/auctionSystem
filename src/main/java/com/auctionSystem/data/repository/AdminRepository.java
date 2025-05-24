package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByUsername(String username);
    boolean existsByEmail(String email);
    Admin findByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Admin> findById(String id);
}

