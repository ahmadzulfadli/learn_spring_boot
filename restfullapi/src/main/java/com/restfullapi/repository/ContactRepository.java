package com.restfullapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.restfullapi.entity.Contact;
import com.restfullapi.entity.User;



@Repository
public interface ContactRepository extends JpaRepository<Contact, String> ,JpaSpecificationExecutor<Contact>{
    
    Optional<Contact> findFirtsByUserAndId(User user, String id);
}
