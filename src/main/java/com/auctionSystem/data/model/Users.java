package com.auctionSystem.data.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@Data
public class Users {
    @Id
    private String id;
    private String fullname;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Roles role = Roles.ADMIN;
    @Indexed(unique = true)
    private String username;
}
