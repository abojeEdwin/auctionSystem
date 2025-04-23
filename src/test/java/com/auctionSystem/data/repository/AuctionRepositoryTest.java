package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.AuctionStatus;
import com.auctionSystem.data.model.Roles;
import com.auctionSystem.data.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
class AuctionRepositoryTest {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        auctionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        auctionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void saveAuctionTest(){
        Auction auction = new Auction();

        User user = new User();
        user.setFullname("Edwin wolf");
        user.setEmail("abojeedwin@gmail.com");
        user.setPassword("password");
        user.setUsername("choko@08");
        user.setRole(Roles.USER);
        userRepository.save(user);


        auction.setTitle("Toyota Evil Spirit");
        auction.setDescription("Black,V8 engine");
        auction.setStatus(AuctionStatus.PENDING);
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        auction.setSellerId(user.getId());
        auction.setStartingPrice(17000.00);
        auctionRepository.save(auction);

        assert auctionRepository.findById(auction.getId()).isPresent();
    }

    @Test
    public void findAuctionByIdTest(){
        Auction auction = new Auction();

        User user = new User();

        user.setFullname("Edwin wolf");
        user.setEmail("abojeedwin@gmail.com");
        user.setPassword("password");
        user.setUsername("choko@08");
        user.setRole(Roles.USER);
        userRepository.save(user);


        auction.setTitle("Toyota Evil Spirit");
        auction.setDescription("Black,V8 engine");
        auction.setStatus(AuctionStatus.PENDING);
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        auction.setStartingPrice(17000.00);
        auctionRepository.save(auction);
        assert auctionRepository.count() == 1;
        Auction foundAuction = auctionRepository.findById(auction.getId()).get();
        assert foundAuction.getTitle().equals(auction.getTitle());

    }

}