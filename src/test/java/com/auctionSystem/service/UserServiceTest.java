package com.auctionSystem.service;
import com.auctionSystem.controller.BidSocketController;
import com.auctionSystem.data.model.*;
import com.auctionSystem.data.repository.AdminRepository;
import com.auctionSystem.data.repository.AuctionRepository;
import com.auctionSystem.data.repository.UserRepository;
import com.auctionSystem.dtos.LoginRequest;
import com.auctionSystem.dtos.UserResponse;
import com.auctionSystem.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    BidSocketController bidSocketController;

    @BeforeEach
    void setUp() {
        userService.deleteAll();
        auctionRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userService.deleteAll();
        auctionRepository.deleteAll();
        adminRepository.deleteAll();
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
        UserResponse userResponse = userService.login(loginRequest);
        assert userResponse != null;
        assert userResponse.getEmail().equals("email@gmail.com");
        assert userResponse.getUsername().equals("DarkKnight@14");

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

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email@gmail.com");
        loginRequest.setPassword("password");
        UserResponse userResponse = userService.login(loginRequest);
        assert userResponse != null;


        Auction auction = new Auction();
        auction.setTitle("Auction Tittle");
        auction.setDescription("Auction Description");
        auction.setCurrentPrice(150.0);
        auction.setStartingPrice(100.00);
        auction.setStatus(AuctionStatus.PENDING);
        auction.setSellerId(user.getId());
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
        auction.setSellerId(user.getId());
        auction.setStatus(AuctionStatus.PENDING);
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        Auction savedAuction = userService.createAuction(auction);
        assert savedAuction.getTitle().equals("Auction Tittle");

        userService.deleteAuctionById(savedAuction.getId());
        assert auctionRepository.count() == 0;
    }

    @Test
    public void placeBidTest(){
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


        User seller = new User();
        seller.setFullname("Amali Precious");
        seller.setRole(Roles.USER);
        seller.setUsername("DarkKnight@14");
        seller.setPassword("password");
        seller.setEmail("email@gmail.com");
        userService.register(seller);

        Auction auction = new Auction();
        auction.setTitle("Auction Tittle");
        auction.setDescription("Auction Description");
        auction.setCurrentPrice(150.0);
        auction.setStartingPrice(100.00);
        auction.setSellerId(seller.getId());
        auction.setStatus(AuctionStatus.PENDING);
        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
        Auction savedAuction = userService.createAuction(auction);
        assert savedAuction.getTitle().equals("Auction Tittle");

        Auction changedAuction = adminService.verifyListedAuction(auction);
        assert changedAuction.getTitle().equals("Auction Tittle");
        assert changedAuction.getStatus() == AuctionStatus.ACTIVE;

        User buyer = new User();
        buyer.setFullname("Joseph King");
        buyer.setRole(Roles.USER);
        buyer.setUsername("RastaMan123");
        buyer.setPassword("password");
        buyer.setEmail("reggeneverdies@gmail.com");
        userService.register(buyer);

        Bid bidPlaced = new Bid();
        bidPlaced.setBidderId(buyer.getId());
        bidPlaced.setAmount(1500.0);
        bidPlaced.setAuctionItemId(changedAuction.getId());
        Bid savedBid = userService.placeBid(bidPlaced);
        Bid notifiedBid = bidSocketController.getLastNotifiedBid();
        assert notifiedBid.getBidderId().equals(buyer.getId());
        assert notifiedBid.getAmount() == 1500.0;


    }

//    @Test
//    public void bidsCannotBePlacedWhenAuctionIsPendingTest(){
//
//        Admin admin = new Admin();
//        admin.setUsername("Supreme admin");
//        admin.setPassword("password");
//        admin.setRole(Roles.ADMIN);
//        admin.setEmail("adminonly@gmail.com");
//        admin.setFullname("Admin Yoda");
//        Admin savedAdmin = adminService.register(admin);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("adminonly@gmail.com");
//        loginRequest.setPassword("password");
//        Admin loginAdmin = adminService.login(loginRequest);
//
//        User seller = new User();
//        seller.setFullname("Amali Precious");
//        seller.setRole(Roles.USER);
//        seller.setUsername("DarkKnight@14");
//        seller.setPassword("password");
//        seller.setEmail("email@gmail.com");
//        userService.register(seller);
//
//        Auction auction = new Auction();
//        auction.setTitle("Auction Tittle");
//        auction.setDescription("Auction Description");
//        auction.setCurrentPrice(150.0);
//        auction.setStartingPrice(100.00);
//        auction.setSellerId(seller.getId());
//        auction.setStatus(AuctionStatus.PENDING);
//        auction.setEndTime(Instant.now().plus(2, ChronoUnit.HOURS));
//        Auction savedAuction = userService.createAuction(auction);
//        assert savedAuction.getTitle().equals("Auction Tittle");
//
//        User buyer = new User();
//        buyer.setFullname("Joseph King");
//        buyer.setRole(Roles.USER);
//        buyer.setUsername("RastaMan123");
//        buyer.setPassword("password");
//        buyer.setEmail("reggeneverdies@gmail.com");
//        userService.register(buyer);
//
//        Bid bidPlaced = new Bid();
//        bidPlaced.setBidderId(buyer.getId());
//        bidPlaced.setAmount(1500.0);
//        bidPlaced.setAuctionItemId(auction.getId());
//        assertThrows(PendingBidException.class,()->userService.placeBid(bidPlaced));
//        Auction changedAuction = adminService.verifyListedAuction(auction);
////        assert changedAuction.getSeller().getFullname().equals("Amali Precious");
//        assert changedAuction.getStatus() == AuctionStatus.ACTIVE;
    //}

}