package com.auctionSystem.service;
import com.auctionSystem.controller.BidSocketController;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.AuctionStatus;
import com.auctionSystem.data.repository.AuctionRepository;
import com.auctionSystem.data.repository.BidRepository;
import com.auctionSystem.exceptions.AuctionNotFoundException;
import com.auctionSystem.exceptions.NullAuctionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuctionService {

    @Autowired
    private BidSocketController bidSocketController;
    @Autowired
    private AuctionRepository auctionRepository;

    public Auction createAuction(Auction auction){
        if(auction.getDescription().isEmpty() || auction.getTitle().isEmpty()){throw new NullAuctionException("Auction values cannot be empty");}
        if(auction.getSellerId() == null || auction.getStatus() == null){throw new NullAuctionException("Auction values cannot be empty");}
        if(auction.getStartingPrice() <= 0 || auction.getCurrentPrice() <= 0 ){throw new NullAuctionException("Auction prices cannot be less than 0");}
        auction.setStartingPrice(auction.getCurrentPrice());
        Instant endTime = Instant.now().plus(3, ChronoUnit.HOURS);
        auction.setEndTime(endTime);
        bidSocketController.notifyAuctionStatus(auction);
        return auctionRepository.save(auction);
    }

    public Auction verifyListedAuction(String auctionId) {
        Auction foundAuction = auctionRepository.findAuctionById(auctionId);
        if (foundAuction.getStatus() != AuctionStatus.PENDING) {
            throw new AuctionNotFoundException(
                    "Auction " + auctionId + " has invalid status: " + foundAuction.getStatus());
        }
        foundAuction.setStatus(AuctionStatus.ACTIVE);
        Auction activatedAuction = auctionRepository.save(foundAuction);
        bidSocketController.notifyAuctionStatus(activatedAuction);
        return activatedAuction;
    }
}
