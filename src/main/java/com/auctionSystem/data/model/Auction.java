package com.auctionSystem.data.model;
import jakarta.validation.constraints.NotNull;
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
    @Id @NotNull
    private String id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private double startingPrice;
    @NotNull
    private double currentPrice;
    @NotNull
    private Instant endTime;
    @NotNull
    private User seller;
    @NotNull
    private AuctionStatus status = AuctionStatus.PENDING;
}
