package com.auctionSystem.controller;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.Bid;
import com.auctionSystem.dtos.AuctionVerificationRequest;
import com.auctionSystem.exceptions.AuctionNotFoundException;
import com.auctionSystem.service.AuctionService;
import com.auctionSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/api")
public class AuctionController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuctionService auctionService;

    @PostMapping("/createAuction")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        Auction auctionSaved = userService.createAuction(auction);
        return new ResponseEntity<>(auctionSaved, HttpStatus.CREATED);
    }

    @PostMapping("/placeBid")
    public ResponseEntity<Bid> placeBid(@RequestBody Bid bid) {
        userService.placeBid(bid);
        return new ResponseEntity<>(bid, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAuction")
    public ResponseEntity<Boolean> deleteAuction(@Valid @RequestBody AuctionVerificationRequest request) {
        userService.deleteAuctionById(request.getAuctionId());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @PostMapping("/verifyListedAuction")
    public ResponseEntity<?> verifyListedAuction(@Valid @RequestBody AuctionVerificationRequest request) {
        try {Auction auction = auctionService.verifyListedAuction(request.getAuctionId());
            if (auction == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Auction is not in PENDING status");}
            return ResponseEntity.ok(auction);
        } catch (AuctionNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("Error processing request: " + ex.getMessage());
        }
    }

}
