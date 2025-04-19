package com.auctionSystem.data.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;


@Document(collection="Auction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    private String id;
    private String title;
    private String description;
    private double startingPrice;
    private double currentPrice;
    private Instant endTime;
    private User seller;
    private AuctionStatus status = AuctionStatus.PENDING;
}
