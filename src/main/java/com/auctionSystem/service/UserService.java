package com.auctionSystem.service;
import com.auctionSystem.Util.HashPassword;
import com.auctionSystem.Util.ValidateBid;
import com.auctionSystem.Util.VerifyEmail;
import com.auctionSystem.controller.BidSocketController;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.AuctionStatus;
import com.auctionSystem.data.model.Bid;
import com.auctionSystem.data.model.User;
import com.auctionSystem.data.repository.AuctionRepository;
import com.auctionSystem.data.repository.BidRepository;
import com.auctionSystem.data.repository.UserRepository;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.dtos.UserResponse;
import com.auctionSystem.exceptions.*;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class UserService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    private BidSocketController bidSocketController;
    @Autowired
    JwtService jwtService;
    @Autowired
    HashPassword hashPassword;
    @Autowired
    ValidateBid validateBid;
    @Autowired
    AuctionService auctionService;
    @Autowired
    BidService bidService;


    public User register(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {throw new DuplicateUserNameException("Username already exists");}
        if(userRepository.existsByEmail(user.getEmail())) {throw new DuplicateEmailException("Email already exists");}
        if (!VerifyEmail.isValidEmail(user.getEmail())){throw new InvalidEmailException("Invalid email");};
        user.setPassword(HashPassword.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user == null){ throw new UserNotFoundException("user not found");};
        if(!HashPassword.verifyPassword(user.getPassword(),loginRequest.getPassword())) {throw new InvalidPasswordException("incorrect password");}
        String token = jwtService.generateToken(user.getUsername());
        return new UserResponse(token,user.getId(),user.getEmail(),user.getUsername());
    }

    public Auction createAuction(Auction auction) {
       return auctionService.createAuction(auction);
    }

    public ResponseEntity<Boolean> deleteAuctionById(@NotNull String id) {
        auctionRepository.deleteById(id);
        return null;
    }

    public Bid placeBid(Bid bidPlaced) {
        validateBid.validateBid(bidPlaced);
        validateBid.validateAuction(bidPlaced);
        Bid savedBid = bidRepository.save(bidPlaced);
        bidSocketController.notifyNewBid(savedBid);
        return savedBid;
    }


    public Bid getBidHistory(@NotNull String auctionItemId) {
     return bidService.getBidHistory(auctionItemId);
    }

    public Bid getCurrentBid() {
        return bidSocketController.getLastNotifiedBid();
    }

    public Auction getCurrentAuctionStatus() {
        return bidSocketController.getNotifiedAuctionStatus();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public long count() {
        return userRepository.count();
    }
}
