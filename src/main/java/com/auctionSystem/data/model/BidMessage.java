package com.auctionSystem.data.model;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BidMessage {

    private double amount;
    private String bidder;

}
