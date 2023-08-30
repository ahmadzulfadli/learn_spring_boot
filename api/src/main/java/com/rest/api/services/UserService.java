package com.rest.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rest.api.models.User;
import com.rest.api.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User insertUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public User updatUser(Long id, User user){
        user.setId(id);
        userRepository.save(user);

        return user;
    }

    public User getUserById(Long id){
        User getUser= userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return getUser;

    }

    @Transactional
    public void deleteUser(Long id){
        User deleteUser= userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(deleteUser);  
    }

    public List<User> getList(){
        return userRepository.findAll();
    }

}
