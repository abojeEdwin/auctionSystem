package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class BidRepositoryTest {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        bidRepository.deleteAll();
        userRepository.deleteAll();
        auctionRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        bidRepository.deleteAll();
        userRepository.deleteAll();
        auctionRepository.deleteAll();
    }

    @Test
    public void saveBidTest(){
        User user = new User();
        user.setFullname("Edwin me");
        user.setEmail("abojeedwin@gmail.com");
        user.setPassword("password");
        user.setUsername("choko@08");
        user.setRole(Roles.USER);
        userRepository.save(user);

        User user1 = new User();
        user1.setFullname("sammy");
        user1.setEmail("omachoko@gmail.com");
        user1.setPassword("password");
        user1.setUsername("cyber1@17");
        user1.setRole(Roles.USER);
        userRepository.save(user1);

        Auction auction = new Auction();

        auction.setStatus(AuctionStatus.PENDING);
        auction.setDescription("Laptop");
        auction.setStartingPrice(1500.00);
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        auction.setCurrentPrice(1550.00);
        auctionRepository.save(auction);

        Bid bid = new Bid();
        bid.setBidderId(user1.getId());
        bid.setAmount(1603.998);
        bid.setAuctionItemId(auction.getId());
        bid.setTimestamp(Instant.now());
        bidRepository.save(bid);
        assertEquals(1,bidRepository.count());
    }

    @Test
    public void findAllBidsTest(){
        User userseller = new User();
        userseller.setFullname("Edwin me");
        userseller.setEmail("abojeedwin@gmail.com");
        userseller.setPassword("password");
        userseller.setUsername("choko@08");
        userseller.setRole(Roles.USER);
        userRepository.save(userseller);

        User userbidder1 = new User();
        userbidder1.setFullname("samson you");
        userbidder1.setEmail("abj@gmail.com");
        userbidder1.setPassword("password");
        userbidder1.setUsername("chiken@8");
        userbidder1.setRole(Roles.USER);
        userRepository.save(userbidder1);

        User userbidder2 = new User();
        userbidder2.setFullname("sammy");
        userbidder2.setEmail("omachoko@gmail.com");
        userbidder2.setPassword("password");
        userbidder2.setUsername("cyber1@17");
        userbidder2.setRole(Roles.USER);
        userRepository.save(userbidder2);

        Auction auction = new Auction();
        auction.setStatus(AuctionStatus.PENDING);
        auction.setDescription("Laptop: 8gb ram ");
        auction.setStartingPrice(1500.00);
        auction.setSellerId(userseller.getId());
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        auction.setCurrentPrice(1550.00);
        auctionRepository.save(auction);

        Bid bid = new Bid();
        bid.setBidderId(userbidder1.getId());
        bid.setAmount(1603.998);
        bid.setAuctionItemId(auction.getId());
        bid.setTimestamp(Instant.now());
        bidRepository.save(bid);

        Bid secondbidder = new Bid();
        secondbidder.setBidderId(userbidder2.getId());
        secondbidder.setAuctionItemId(auction.getId());
        secondbidder.setAmount(1700.00);
        secondbidder.setTimestamp(Instant.now());
        bidRepository.save(secondbidder);

        assertEquals(2,bidRepository.count());
        List<Bid> foundBids = new ArrayList<>();
        foundBids = bidRepository.findAll();
        assertEquals(2,foundBids.size());

    }

    @Test
    public void findBidByIdTest(){
        User userseller = new User();
        userseller.setFullname("Edwin me");
        userseller.setEmail("abojeedwin@gmail.com");
        userseller.setPassword("password");
        userseller.setUsername("choko@08");
        userseller.setRole(Roles.USER);
        userRepository.save(userseller);

        User userbidder1 = new User();
        userbidder1.setFullname("samson you");
        userbidder1.setEmail("abj@gmail.com");
        userbidder1.setPassword("password");
        userbidder1.setUsername("chiken@8");
        userbidder1.setRole(Roles.USER);
        userRepository.save(userbidder1);

        User userbidder2 = new User();
        userbidder2.setFullname("sammy");
        userbidder2.setEmail("omachoko@gmail.com");
        userbidder2.setPassword("password");
        userbidder2.setUsername("cyber1@17");
        userbidder2.setRole(Roles.USER);
        userRepository.save(userbidder2);

        Auction auction = new Auction();
        auction.setStatus(AuctionStatus.PENDING);
        auction.setDescription("Laptop: 8gb ram ");
        auction.setStartingPrice(1500.00);
        auction.setSellerId(userseller.getId());
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        auction.setCurrentPrice(1550.00);
        auctionRepository.save(auction);

        Bid bid = new Bid();
        bid.setBidderId(userbidder1.getId());
        bid.setAmount(1603.998);
        bid.setAuctionItemId(auction.getId());
        bid.setTimestamp(Instant.now());
        bidRepository.save(bid);

        Bid secondbidder = new Bid();
        secondbidder.setAuctionItemId(userbidder2.getId());
        secondbidder.setAuctionItemId(auction.getId());
        secondbidder.setAmount(1700.00);
        secondbidder.setTimestamp(Instant.now());
        bidRepository.save(secondbidder);

        Bid foundBids = bidRepository.findBidsById(bid.getId());
        assertEquals( 1603.998,foundBids.getAmount());

    }

    @Test
    public void deleteBidByIdTest(){
        User userseller = new User();
        userseller.setFullname("Edwin me");
        userseller.setEmail("abojeedwin@gmail.com");
        userseller.setPassword("password");
        userseller.setUsername("choko@08");
        userseller.setRole(Roles.USER);
        userRepository.save(userseller);

        User userbidder1 = new User();
        userbidder1.setFullname("samson you");
        userbidder1.setEmail("abj@gmail.com");
        userbidder1.setPassword("password");
        userbidder1.setUsername("chiken@8");
        userbidder1.setRole(Roles.USER);
        userRepository.save(userbidder1);

        User userbidder2 = new User();
        userbidder2.setFullname("sammy");
        userbidder2.setEmail("omachoko@gmail.com");
        userbidder2.setPassword("password");
        userbidder2.setUsername("cyber1@17");
        userbidder2.setRole(Roles.USER);
        userRepository.save(userbidder2);

        Auction auction = new Auction();

        auction.setStatus(AuctionStatus.PENDING);
        auction.setDescription("Laptop: 8gb ram ");
        auction.setStartingPrice(1500.00);
        auction.setSellerId(userseller.getId());
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        auction.setCurrentPrice(1550.00);
        auctionRepository.save(auction);

        Bid bid = new Bid();
        bid.setBidderId(userbidder1.getId());
        bid.setAmount(1603.998);
        bid.setAuctionItemId(auction.getId());
        bid.setTimestamp(Instant.now());
        bidRepository.save(bid);

        Bid secondbidder = new Bid();
        secondbidder.setBidderId(userbidder2.getId());
        secondbidder.setAuctionItemId(auction.getId());
        secondbidder.setAmount(1700.00);
        secondbidder.setTimestamp(Instant.now());
        bidRepository.save(secondbidder);

        assert bidRepository.count() == 2;
        bidRepository.deleteById(bid.getId());
        assert bidRepository.count() == 1;
    }

}