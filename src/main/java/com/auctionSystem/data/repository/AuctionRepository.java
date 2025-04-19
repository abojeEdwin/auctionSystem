package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.Auction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends MongoRepository<Auction, String> {


}
