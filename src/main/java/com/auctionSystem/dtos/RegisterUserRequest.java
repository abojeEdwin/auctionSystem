package com.auctionSystem.dtos;
import com.auctionSystem.data.model.User;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private User user;
}
