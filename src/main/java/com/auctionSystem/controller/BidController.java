package com.auctionSystem.controller;
import com.auctionSystem.data.model.Bid;
import com.auctionSystem.dtos.AuctionVerificationRequest;
import com.auctionSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user/api")
public class BidController {

    @Autowired
    private UserService userService;

    @GetMapping("/getBidHistory")
    public ResponseEntity<List<Bid>> getBidHistory(@Valid @RequestBody AuctionVerificationRequest request) {
        userService.getBidHistory(request.getAuctionId());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
