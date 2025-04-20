package com.auctionSystem.controller;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.Bid;
import com.auctionSystem.data.model.BidMessage;
import com.auctionSystem.data.model.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class BidSocketController {

    SimpMessagingTemplate messagingTemplate;

    // Call this when a new bid is placed
    public void notifyNewBid(Auction auction, Bid bid) {
        messagingTemplate.convertAndSend(
                "/topic/auction/" + auction.getId() + "/bids",
                new BidMessage(bid.getAmount(), bid.getBidder().getUsername())
        );
    }

    // Call this when auction status changes
    public void notifyAuctionStatus(Auction auction) {
        messagingTemplate.convertAndSend(
                "/topic/auction/" + auction.getId() + "/status",
                new StatusMessage(auction.getStatus(), auction.getEndTime())
        );
    }
}
