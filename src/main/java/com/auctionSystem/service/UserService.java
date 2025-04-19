package com.auctionSystem.service;
import com.auctionSystem.data.model.User;
import com.auctionSystem.data.repository.BidRepository;
import com.auctionSystem.data.repository.UserRepository;
import com.auctionSystem.dtos.RegisterUserRequest;
import com.auctionSystem.exceptions.DuplicateEmailException;
import com.auctionSystem.exceptions.DuplicateUserNameException;
import com.auctionSystem.exceptions.InvalidEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
public class UserService {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static String EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidRepository bidRepository;

    public User register(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {throw new DuplicateUserNameException("Username already exists");}
        if(userRepository.existsByEmail(user.getEmail())) {throw new DuplicateEmailException("Email already exists");}
        if(!isValidEmail(user.getEmail())){throw new InvalidEmailException("Please enter a valid email");};

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public long count() {
        return userRepository.count();
    }

    public static String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }


    public User login(String password, String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }
}
