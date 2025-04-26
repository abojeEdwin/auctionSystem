package com.auctionSystem.controller;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.User;
import com.auctionSystem.dtos.AuctionVerificationRequest;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.dtos.UserResponse;
import com.auctionSystem.exceptions.AuctionNotFoundException;
import com.auctionSystem.exceptions.UserNotFoundException;
import com.auctionSystem.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/admin/api")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@Valid @RequestBody Admin admin) {
        Admin savedAdmin = adminService.register(admin);
        if (savedAdmin == null || savedAdmin.getId() == null ||savedAdmin.getFullname().isEmpty()|| savedAdmin.getFullname().equals(" ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse savedAdmin = adminService.login(loginRequest);
        if(savedAdmin == null){throw new UserNotFoundException("USER NOT FOUND");}
        return new ResponseEntity<>(savedAdmin, HttpStatus.OK);
    }

    @PostMapping("/verifyListedAuction")
    public ResponseEntity<?> verifyListedAuction(@Valid @RequestBody AuctionVerificationRequest request) {
        try {
            Auction auction = adminService.verifyListedAuction(request.getAuctionId());

            if (auction == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Auction is not in PENDING status");
            }

            return ResponseEntity.ok(auction);
        } catch (AuctionNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("Error processing request: " + ex.getMessage());
        }
    }
}
