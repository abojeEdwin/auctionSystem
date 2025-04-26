package com.auctionSystem.service;
import com.auctionSystem.controller.BidSocketController;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.AuctionStatus;
import com.auctionSystem.data.model.User;
import com.auctionSystem.data.repository.AdminRepository;
import com.auctionSystem.data.repository.AuctionRepository;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.dtos.UserResponse;
import com.auctionSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;


@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    BidSocketController bidSocketController;

    @Autowired
    JwtService jwtService;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static String EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


    public Admin register(Admin admin) {
        if(adminRepository.existsByUsername(admin.getUsername())) {throw new DuplicateUserNameException("Username already exists");}
        if(adminRepository.existsByEmail(admin.getEmail())) {throw new DuplicateEmailException("Email already exists");}
        if(!isValidEmail(admin.getEmail())){throw new InvalidEmailException("Please enter a valid email");};
        if(admin.getPassword().isEmpty() || admin.getFullname().isEmpty()){throw new InvalidCredentialException("Please enter valid credentials");}
        if(admin.getFullname().equals(" ") || admin.getFullname().equals("")){throw new InvalidCredentialException("Please enter valid credentials");}

        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);
        return adminRepository.save(admin);
    }

    public UserResponse login(LoginRequest loginRequest) {
        Admin foundAdmin = adminRepository.findByEmail(loginRequest.getEmail());
        if(!(Objects.equals(loginRequest.getEmail(), loginRequest.getEmail()))){ throw new UserNotFoundException("user not found");};
        if(!verifyPassword(foundAdmin.getPassword(),loginRequest.getPassword())) {throw new InvalidPasswordException("incorrect password");}
        String token = jwtService.generateToken(foundAdmin.getUsername());
        return new UserResponse(token,foundAdmin.getId(),foundAdmin.getEmail(),foundAdmin.getUsername());
    }

    public Auction verifyListedAuction(String auctionId) {
        Auction foundAuction = auctionRepository.findAuctionById(auctionId);
        if (foundAuction.getStatus() != AuctionStatus.PENDING) {
            throw new AuctionNotFoundException(
                    "Auction " + auctionId + " has invalid status: " + foundAuction.getStatus());
        }
        foundAuction.setStatus(AuctionStatus.ACTIVE);
        Auction activatedAuction = auctionRepository.save(foundAuction);
        bidSocketController.notifyAuctionStatus(activatedAuction);
        return activatedAuction;
    }


    public static String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean verifyPassword(String hashedPassword, String inputPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty() || inputPassword == null || inputPassword.isEmpty()) {return false;}
        try {return passwordEncoder.matches(inputPassword, hashedPassword);} catch (IllegalArgumentException e) {return false;}
    }

    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }
}
