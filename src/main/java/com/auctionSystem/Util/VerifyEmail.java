package com.auctionSystem.Util;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class VerifyEmail {

    private static String EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }
}
