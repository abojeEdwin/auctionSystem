package com.auctionSystem.service;
import com.auctionSystem.data.model.Bid;
import com.auctionSystem.data.repository.BidRepository;
import com.auctionSystem.exceptions.NullAuctionException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    public Bid getBidHistory(@NotNull String auctionItemId){
        if(auctionItemId == null){throw new NullAuctionException("Auction id is required");}
        return bidRepository.findById(auctionItemId).get();
    }
}
