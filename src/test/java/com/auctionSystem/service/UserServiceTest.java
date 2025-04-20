package com.auctionSystem.service;
import com.auctionSystem.data.model.Auction;
import com.auctionSystem.data.model.AuctionStatus;
import com.auctionSystem.data.model.Roles;
import com.auctionSystem.data.model.User;
import com.auctionSystem.data.repository.AuctionRepository;
import com.auctionSystem.data.repository.UserRepository;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @BeforeEach
    void setUp() {
        userService.deleteAll();
        auctionRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userService.deleteAll();
        auctionRepository.deleteAll();
    }

    @Test
    public void registerUserTest() {
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);
        assert userService.count() == 1;
        assertNotNull(user);
    }

    @Test
    public void registerUserWithSameUsernameTest() {
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);
        assert userService.count() == 1;
        assertNotNull(user);

        User user1 = new User();
        user1.setFullname("Rebecca Moses");
        user1.setRole(Roles.USER);
        user1.setUsername("DarkKnight@14");
        user1.setPassword("password");
        user1.setEmail("email@gmail.com");
        assertThrows(DuplicateUserNameException.class,()->userService.register(user1));
    }

    @Test
    public void registerUserWithSameEmailTest() {
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);
        assert userService.count() == 1;
        assertNotNull(user);

        User user1 = new User();
        user1.setFullname("Rebecca Moses");
        user1.setRole(Roles.USER);
        user1.setUsername("DarkKnight@07");
        user1.setPassword("password");
        user1.setEmail("email@gmail.com");
        assertThrows(DuplicateEmailException.class,()->userService.register(user1));

    }

    @Test
    public void registerUserWithInvalidEmailTest(){
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gail");
        assertThrows(InvalidEmailException.class,()->userService.register(user));
        assert userService.count() == 0;
    }

    @Test
    public void loginUserTest(){
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);
        assert userService.count() == 1;
        assertNotNull(user);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email@gmail.com");
        loginRequest.setPassword("password");
       User loginUser =  userService.login(loginRequest);
       assert loginUser.getFullname().equals("Amali Precious");

    }

    @Test
    public void loginUserWithInvalidPasswordTest(){
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);
        assert userService.count() == 1;
        assertNotNull(user);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email@gmail.com");
        loginRequest.setPassword("paassiword");
        assertThrows(InvalidPasswordException.class,()->userService.login(loginRequest));
    }

    @Test
    public void loginUserWithInvalidEmailTest(){
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);
        assert userService.count() == 1;
        assertNotNull(user);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("emailme@gmail.com");
        loginRequest.setPassword("password");
        assertThrows(UserNotFoundException.class,()->userService.login(loginRequest));
    }

    @Test
    public void createAuctionTest(){
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);

        Auction auction = new Auction();
        auction.setTitle("Auction Tittle");
        auction.setDescription("Auction Description");
        auction.setCurrentPrice(150.0);
        auction.setStartingPrice(100.00);
        auction.setStatus(AuctionStatus.PENDING);
        auction.setSeller(user);
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        Auction savedAuction = userService.createAuction(auction);
        assert savedAuction.getTitle().equals("Auction Tittle");
    }

    @Test
    public void deleteAuctionTest(){
        User user = new User();
        user.setFullname("Amali Precious");
        user.setRole(Roles.USER);
        user.setUsername("DarkKnight@14");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userService.register(user);

        Auction auction = new Auction();
        auction.setTitle("Auction Tittle");
        auction.setDescription("Auction Description");
        auction.setCurrentPrice(150.0);
        auction.setStartingPrice(100.00);
        auction.setStatus(AuctionStatus.PENDING);
        auction.setSeller(user);
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        Auction savedAuction = userService.createAuction(auction);
        assert savedAuction.getTitle().equals("Auction Tittle");

        userService.deleteAuctionById(savedAuction.getId());
        assert auctionRepository.count() == 0;
    }

}