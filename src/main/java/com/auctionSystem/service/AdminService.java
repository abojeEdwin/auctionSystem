package com.auctionSystem.service;
import com.auctionSystem.Util.HashPassword;
import com.auctionSystem.Util.VerifyEmail;
import com.auctionSystem.controller.BidSocketController;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.data.repository.AdminRepository;
import com.auctionSystem.data.repository.AuctionRepository;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.dtos.UserResponse;
import com.auctionSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;


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
    @Autowired
    VerifyEmail verifyEmail;



    public Admin register(Admin admin) {
        if(adminRepository.existsByUsername(admin.getUsername())) {throw new DuplicateUserNameException("Username already exists");}
        if(adminRepository.existsByEmail(admin.getEmail())) {throw new DuplicateEmailException("Email already exists");}
        if(!VerifyEmail.isValidEmail(admin.getEmail())){throw new InvalidEmailException("Please enter a valid email");};
        admin.setPassword(HashPassword.hashPassword(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public UserResponse login(LoginRequest loginRequest) {
        Admin foundAdmin = adminRepository.findByEmail(loginRequest.getEmail());
        if(foundAdmin == null){ throw new UserNotFoundException("Admin not found");};
        if(!HashPassword.verifyPassword(foundAdmin.getPassword(),loginRequest.getPassword())) {throw new InvalidPasswordException("incorrect password");}
        String token = jwtService.generateToken(foundAdmin.getUsername());
        return new UserResponse(token,foundAdmin.getId(),foundAdmin.getEmail(),foundAdmin.getUsername());
    }



}
