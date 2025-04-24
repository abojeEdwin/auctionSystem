package com.auctionSystem.controller;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.data.model.User;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/admin/api")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    public ResponseEntity<Admin> register(Admin admin) {
        Admin savedAdmin = adminService.register(admin);
        if (savedAdmin == null || savedAdmin.getId() == null ||savedAdmin.getFullname().isEmpty()|| savedAdmin.getFullname().equals(" ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

//    public ResponseEntity<Admin> login(LoginRequest loginRequest) {
//        Admin savedAdmin = adminService.login(loginRequest);
//    }
}
