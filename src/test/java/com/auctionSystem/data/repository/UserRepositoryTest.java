package com.auctionSystem.data.repository;
import com.auctionSystem.data.model.Roles;
import com.auctionSystem.data.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;



@DataMongoTest
class UserRepositoryTest {

        @Autowired
        UserRepository userRepository;

        @BeforeEach
        void setUp() {
            userRepository.deleteAll();
        }
        @AfterEach
        void tearDown() {
            userRepository.deleteAll();
        }


        @Test
        public void saveUserTest(){
            User user = new User();
            user.setFullname("Edwin me");
            user.setEmail("abojeedwin@gmail.com");
            user.setPassword("password");
            user.setUsername("choko@08");
            user.setRole(Roles.USER);
            userRepository.save(user);
            assertEquals(1,userRepository.count());
        }

        @Test
        public void findUserByUsernameTest(){
            User user = new User();
            user.setEmail("abojeedwin@gmail.com");
            user.setPassword("password");
            user.setUsername("choko@08");
            user.setRole(Roles.USER);
            userRepository.save(user);
            assertEquals(1,userRepository.count());
            User foundUser = userRepository.findByUsername("choko@08");
            assertEquals("choko@08",foundUser.getUsername());
        }

        @Test
        public void findUserByIdTest(){
            User user = new User();
            user.setEmail("abojeedwin@gmail.com");
            user.setPassword("password");
            user.setUsername("choko@08");
            user.setRole(Roles.USER);
            User foundUser = userRepository.save(user);
            String userId = foundUser.getId();
            assertEquals(1,userRepository.count());
            assert userId != null;
        }

        @Test
        public void saveAndDeleteUser(){
            User user = new User();
            user.setEmail("abojeedwin@gmail.com");
            user.setPassword("password");
            user.setUsername("choko@08");
            user.setRole(Roles.USER);
            User savedUser = userRepository.save(user);
            assertEquals(1,userRepository.count());
            userRepository.delete(user);
            assertEquals(0,userRepository.count());
        }

        @Test
        public void findByUserEmailTest(){
            User user = new User();
            user.setFullname("Aboje John Doe");
            user.setEmail("abojeedwin@gmail.com");
            user.setPassword("password");
            user.setUsername("choko@08");
            user.setRole(Roles.USER);
            User savedUser = userRepository.save(user);
            assertEquals(1,userRepository.count());
            User foundUser = userRepository.findByEmail("abojeedwin@gmail.com");
            assertEquals("Aboje John Doe",foundUser.getFullname());

        }




    }