package com.auctionSystem.service;
import com.auctionSystem.data.model.Roles;
import com.auctionSystem.data.model.User;
import com.auctionSystem.data.repository.UserRepository;
import com.auctionSystem.exceptions.DuplicateEmailException;
import com.auctionSystem.exceptions.DuplicateUserNameException;
import com.auctionSystem.exceptions.InvalidEmailException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userService.deleteAll();
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

       User loginUser =  userService.login(user.getPassword(),user.getEmail());
       assert loginUser.getFullname().equals("Amali Precious");

    }

}