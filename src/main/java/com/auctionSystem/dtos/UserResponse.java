package com.auctionSystem.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String token;
    String id;
    String email;
    String username;


}
