package com.apiuser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.apiuser.entity.User;
import com.apiuser.model.RegisterUserRequest;
import com.apiuser.model.UpdateUserRequest;
import com.apiuser.model.UserResponse;
import com.apiuser.repository.UserRepository;
import com.apiuser.security.BCrypt;

import java.util.Objects;

@Service
@Slf4j
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request){

        //validasi data dari request yang diterima saat register
        validationService.validate(request);

        //cek apakah username belum digunakan sama user yang lain
        if(userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Already Register");
        }

        //data yang sudah melewati pengecekan selanjutnya di simpan di entity user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());
        
        //mengirim data ke database melalui interface UserRepository
        userRepository.save(user);
    }

    //method untuk mengembil data dari database melalui kelas UserResponse yang ada pada model
    public UserResponse get(User user){
        return UserResponse.builder()
        .username(user.getUsername())
        .name(user.getName())
        .build();
    }

    @Transactional
    public UserResponse update(User user, UpdateUserRequest request){

        validationService.validate(request);
        log.info("REQUEST : {}", request);

        //pengecekan jika tidak ada nama yang diperbaharui
        if(Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }

        //pengecekan jika tidak ada password yang diperbaharui
        if(Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        //simpan pembaruan
        userRepository.save(user);

        log.info("USER : {}", user.getName());

        return UserResponse.builder()
        .username(user.getUsername())
        .name(user.getName())
        .build();
    }

}
