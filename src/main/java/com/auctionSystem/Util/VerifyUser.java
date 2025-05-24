package com.auctionSystem.Util;
import com.auctionSystem.data.model.User;
import com.auctionSystem.data.repository.UserRepository;
import com.auctionSystem.exceptions.InvalidCredentialException;
import com.auctionSystem.exceptions.InvalidEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyUser {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerifyEmail verifyEmail;


    public void register(User user){
        if(!VerifyEmail.isValidEmail(user.getEmail())){throw new InvalidEmailException("Please enter a valid email");};
        if(user.getPassword().isEmpty() || user.getFullname().isEmpty()){throw new InvalidCredentialException("Please enter valid credentials");}
        if(user.getFullname().equals(" ") || user.getFullname().equals("")){throw new InvalidCredentialException("Please enter valid credentials");}
    }
}
