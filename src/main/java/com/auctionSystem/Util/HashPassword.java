package com.auctionSystem.Util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashPassword {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean verifyPassword(String hashedPassword, String inputPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty() ||
                inputPassword == null || inputPassword.isEmpty()) {
            return false;}
        try {return passwordEncoder.matches(inputPassword, hashedPassword);} catch (IllegalArgumentException e) {return false;}
    }


}
