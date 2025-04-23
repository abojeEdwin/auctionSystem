package com.auctionSystem.data.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotNull @NotEmpty
    @NotBlank(message="Auction title cannot be empty")
    private String title;
    @NotNull @NotBlank(message="Auction description cannot be empty")
    private String description;
    @NotNull
    private double startingPrice;
    @NotNull
    private double currentPrice;
    @NotNull
    private Instant endTime;
    @NotNull @NotEmpty
    @NotBlank(message="Bidder Id cannot be empty or whitespace")
    private String sellerId;
    @NotNull
    private AuctionStatus status = AuctionStatus.PENDING;
}
