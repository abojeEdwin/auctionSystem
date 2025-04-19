package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {

    Bid findBidsById(String id);
}
