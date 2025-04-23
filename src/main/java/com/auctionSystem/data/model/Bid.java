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
    private String bidderId;
    private double amount;
    private String auctionItemId;
    private Instant timestamp = Instant.now();
}
