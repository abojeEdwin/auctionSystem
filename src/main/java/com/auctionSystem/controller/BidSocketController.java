package com.auctionSystem.controller;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.Bid;
import com.auctionSystem.data.model.BidMessage;
import com.auctionSystem.data.model.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BidSocketController {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    private Bid notifyNewBid;

    // Call this when a new bid is placed
    public void notifyNewBid(Bid bid) {
        this.notifyNewBid = bid;
        messagingTemplate.convertAndSend(
                "/topic/auction/" + "/bids",
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

    public Bid getLastNotifiedBid() {
        return notifyNewBid;
    }
}
