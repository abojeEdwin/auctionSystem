package com.auctionSystem.service;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Pattern;


@Slf4j
@Service
public class UserService {




    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static String EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

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

    public User register(User user) {

        if(userRepository.existsByUsername(user.getUsername())) {throw new DuplicateUserNameException("Username already exists");}
        if(userRepository.existsByEmail(user.getEmail())) {throw new DuplicateEmailException("Email already exists");}
        if(!isValidEmail(user.getEmail())){throw new InvalidEmailException("Please enter a valid email");};
        if(user.getPassword().isEmpty() || user.getFullname().isEmpty()){throw new InvalidCredentialException("Please enter valid credentials");}
        if(user.getFullname().equals(" ") || user.getFullname().equals("")){throw new InvalidCredentialException("Please enter valid credentials");}
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public long count() {
        return userRepository.count();
    }

    public static String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }

    public static boolean verifyPassword(String hashedPassword, String inputPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty() ||
                inputPassword == null || inputPassword.isEmpty()) {
            return false;
        }

        try {
            return passwordEncoder.matches(inputPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(!(Objects.equals(user.getEmail(), loginRequest.getEmail()))){ throw new UserNotFoundException("user not found");};
        if(!verifyPassword(user.getPassword(),loginRequest.getPassword())) {throw new InvalidPasswordException("incorrect password");}
        String token = jwtService.generateToken(user.getUsername());
        return new UserResponse(token,user.getId(),user.getEmail(),user.getUsername());
    }

    public Auction createAuction(Auction auction) {

        if(auction.getDescription().isEmpty() || auction.getTitle().isEmpty()){
            throw new NullAuctionException("Auction values cannot be empty");
        }
        if(auction.getSellerId() == null || auction.getStatus() == null){
            throw new NullAuctionException("Auction values cannot be empty");
        }
        if(auction.getStartingPrice() <= 0 || auction.getCurrentPrice() <= 0 ){
            throw new NullAuctionException("Auction prices cannot be less than 0");
        }
        auction.setStartingPrice(auction.getCurrentPrice());
        Instant endTime = Instant.now().plus(3, ChronoUnit.HOURS);
        auction.setEndTime(endTime);
        bidSocketController.notifyAuctionStatus(auction);
        return auctionRepository.save(auction);
    }

    public ResponseEntity<Boolean> deleteAuctionById(@NotNull String id) {
        auctionRepository.deleteById(id);
        return null;
    }

    public Bid placeBid(Bid bidPlaced) {
        validateBid(bidPlaced);
        validateAuction(bidPlaced);
        Bid savedBid = bidRepository.save(bidPlaced);
        bidSocketController.notifyNewBid(savedBid);
        return savedBid;
    }


    private void  validateBid(Bid bid){
       if(bid.getAuctionItemId().isEmpty() || bid.getAuctionItemId() .equals(" ")){throw new NullAuctionException("Auction item id cannot be empty");}
       if(bid.getAmount()<=0){throw new NullAuctionException("Auction amount cannot be less than 0");}
       if(bid.getTimestamp()==null){throw new NullAuctionException("Timestamp cannot be null");}
       if(bid.getBidderId()==null || bid.getBidderId().equals(" ") || bid.getBidderId().isEmpty()){throw new NullAuctionException("Bidder id cannot be null");}
    }

    private void validateAuction(Bid bidPlaced){
        Auction auctionItem = auctionRepository.findAuctionById(bidPlaced.getAuctionItemId());
        if(auctionItem.getStatus()== AuctionStatus.PENDING){throw new AuctionNotFoundException("Auction item is still pending");}
        if(bidPlaced.getAmount() < auctionItem.getStartingPrice() || bidPlaced.getAmount() < auctionItem.getCurrentPrice()){ throw new NullAuctionException("Bid amount must be greater than or equal to the starting price");}
    }

    public Bid getBidHistory(@NotNull String auctionItemId) {
        if(auctionItemId == null){throw new NullAuctionException("Auction id is required");}
        return bidRepository.findById(auctionItemId).get();
    }

    public Bid getCurrentBid() {
        return bidSocketController.getLastNotifiedBid();
    }

    public Auction getCurrentAuctionStatus() {
        return bidSocketController.getNotifiedAuctionStatus();
    }
}
