package com.auctionSystem.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

   private String secretKey;

   public JwtService() {
       secretKey = generateSecretKey();
   }


    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*3))
                .signWith(getKey(),SignatureAlgorithm.HS256).compact();
    }

    public String generateSecretKey(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("SecretKey: " + secretKey.toString());
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    private Key getKey() {
        byte[] keybytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keybytes);
    }
}
