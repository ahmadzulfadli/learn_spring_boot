package com.rest.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return userRepository.getById(id);
    }
    @Transactional
    public void deleteUser(Long id){
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }
    public List<User> getList(){
        return userRepository.findAll();
    }

}
