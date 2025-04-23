package com.auctionSystem.controller;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.User;
import com.auctionSystem.data.repository.UserRepository;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.dtos.UserResponse;
import com.auctionSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid@RequestBody User user) {
        User userSaved = userService.register(user);
        if (userSaved == null || userSaved.getFullname().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid@RequestBody LoginRequest loginRequest) {
        UserResponse loginResponse = userService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/createAuction")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        Auction auctionSaved = userService.createAuction(auction);
        return new ResponseEntity<>(auctionSaved, HttpStatus.CREATED);
    }

}
