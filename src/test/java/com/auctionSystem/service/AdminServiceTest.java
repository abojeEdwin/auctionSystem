package com.auctionSystem.service;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.data.model.Roles;
import com.auctionSystem.data.repository.AdminRepository;
import com.auctionSystem.dtos.LoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        adminRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
    }

    @Test
    public void registerAdmin() {
        Admin admin = new Admin();
        admin.setUsername("Supreme admin");
        admin.setPassword("password");
        admin.setRole(Roles.ADMIN);
        admin.setEmail("adminonly@gmail.com");
        admin.setFullname("Admin Yoda");
        Admin savedAdmin = adminService.register(admin);
        Assertions.assertNotNull(savedAdmin.getId());
    }

    @Test
    public void loginAdmin() {
        Admin admin = new Admin();
        admin.setUsername("Supreme admin");
        admin.setPassword("password");
        admin.setRole(Roles.ADMIN);
        admin.setEmail("adminonly@gmail.com");
        admin.setFullname("Admin Yoda");
        Admin savedAdmin = adminService.register(admin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("adminonly@gmail.com");
        loginRequest.setPassword("password");
        Admin loginAdmin = adminService.login(loginRequest);
        Assertions.assertNotNull(loginAdmin.getId());

    }

}