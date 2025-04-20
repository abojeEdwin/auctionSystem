package com.auctionSystem.exceptions;

public class PendingBidException extends RuntimeException {
    public PendingBidException(String message) {
        super(message);
    }
}
