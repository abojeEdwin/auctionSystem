package com.auctionSystem.data.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection= "Bid")
@Data
@NoArgsConstructor
public class Bid {
    @Id
    private String id;
    @NotBlank(message = "Bidder Id cannot be empty or whitespace")
    @NotEmpty
    private String bidderId;
    @NotBlank(message="This field is required")
    private double amount;
    @NotBlank(message="Auction Item Id Cannot Be Empty")
    @NotEmpty
    private String auctionItemId;
    @NotBlank(message="This field is required")
    @NotEmpty
    private Instant timestamp = Instant.now();
}
