package com.auctionSystem.data.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Email(message = "Email cannot be empty")
    private String email;
    private String password;
    private Roles role = Roles.USER;
    @Indexed(unique = true)
    private String username;
}
