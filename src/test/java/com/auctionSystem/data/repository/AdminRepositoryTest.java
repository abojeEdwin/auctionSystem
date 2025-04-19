package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.Admin;
import com.auctionSystem.data.model.Roles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        adminRepository.deleteAll();
    }

    @AfterEach
    void tearDown(){
        adminRepository.deleteAll();
    }

    @Test
    public void saveAdminTest(){
        Admin admin = new Admin();
        admin.setFullname("Mr Roger");
        admin.setEmail("roger@gmail.com");
        admin.setPassword("password");
        admin.setRole(Roles.ADMIN);
        admin.setUsername("roger442");
        adminRepository.save(admin);
        assert adminRepository.count() == 1;
    }

    @Test
    public void saveAndDeleteAdminTest(){
        Admin admin = new Admin();
        admin.setFullname("Mr Roger");
        admin.setEmail("roger@gmail.com");
        admin.setPassword("password");
        admin.setRole(Roles.ADMIN);
        admin.setUsername("roger442");
        adminRepository.save(admin);
        assert adminRepository.count() == 1;

        adminRepository.delete(admin);
        assert adminRepository.count() == 0;
    }

}