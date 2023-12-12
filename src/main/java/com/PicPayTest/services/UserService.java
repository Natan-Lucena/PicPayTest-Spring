package com.PicPayTest.services;

import java.math.BigDecimal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PicPayTest.domain.user.User;
import com.PicPayTest.domain.user.UserType;
import com.PicPayTest.dtos.UserDTO;
import com.PicPayTest.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Merchant cant do transactions"); 
        }
        if(sender.getBalance().compareTo(amount) < 0){
             throw new Exception("User does not have balance");
        }
    }
    
    public User findUserById(Long id)  throws Exception{
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }
    public List<User> getAllUsers(){
        return this.repository.findAll();
    }
}
