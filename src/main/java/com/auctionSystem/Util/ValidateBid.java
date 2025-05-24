package com.auctionSystem.Util;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.AuctionStatus;
import com.auctionSystem.data.model.Bid;
import com.auctionSystem.data.repository.AuctionRepository;
import com.auctionSystem.exceptions.AuctionNotFoundException;
import com.auctionSystem.exceptions.NullAuctionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateBid {
    @Autowired
    private AuctionRepository auctionRepository;


    public void  validateBid(Bid bid){
        if(bid.getAuctionItemId().isEmpty() || bid.getAuctionItemId() .equals(" ")){throw new NullAuctionException("Auction item id cannot be empty");}
        if(bid.getAmount()<=0){throw new NullAuctionException("Auction amount cannot be less than 0");}
        if(bid.getTimestamp()==null){throw new NullAuctionException("Timestamp cannot be null");}
        if(bid.getBidderId()==null || bid.getBidderId().equals(" ") || bid.getBidderId().isEmpty()){throw new NullAuctionException("Bidder id cannot be null");}
    }

    public void validateAuction(Bid bidPlaced){
        Auction auctionItem = auctionRepository.findAuctionById(bidPlaced.getAuctionItemId());
        if(auctionItem.getStatus()== AuctionStatus.PENDING){throw new AuctionNotFoundException("Auction item is still pending");}
        if(bidPlaced.getAmount() < auctionItem.getStartingPrice() || bidPlaced.getAmount() < auctionItem.getCurrentPrice()){ throw new NullAuctionException("Bid amount must be greater than or equal to the starting price");}
    }
}
