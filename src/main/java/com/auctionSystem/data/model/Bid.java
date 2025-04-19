package com.auctionSystem.data.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.Instant;

@Document(collection= "Bid")
@Data
@NoArgsConstructor
public class Bid {
    @Id
    private String id;
    private User bidder;
    private double amount;
    private Auction auction;
    private Instant timestamp = Instant.now();
}
