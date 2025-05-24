package com.auctionSystem.controller;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.dtos.UserResponse;
import com.auctionSystem.exceptions.UserNotFoundException;
import com.auctionSystem.service.AdminService;
import com.auctionSystem.service.AuctionService;
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
    @Autowired
    private AuctionService auctionService;

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@Valid @RequestBody Admin admin) {
        Admin savedAdmin = adminService.register(admin);
        if (savedAdmin == null || savedAdmin.getId() == null ||savedAdmin.getFullname().isEmpty()|| savedAdmin.getFullname().equals(" ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse savedAdmin = adminService.login(loginRequest);
        if(savedAdmin == null){throw new UserNotFoundException("USER NOT FOUND");}
        return new ResponseEntity<>(savedAdmin, HttpStatus.OK);
    }

}
