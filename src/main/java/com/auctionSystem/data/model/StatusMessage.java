package com.auctionSystem.data.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;


@Data
@AllArgsConstructor
public class StatusMessage {
    private AuctionStatus status;
    private Instant endTime;

}
