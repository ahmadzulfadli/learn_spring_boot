package com.restfullapi.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfullapi.entity.Address;
import com.restfullapi.entity.Contact;

@Repository
public interface AddressRepository extends JpaRepository<Address ,String>{
    Optional<Address> findFirstByContactAndId(Contact contact, String id);
    List<Address> findAllByContact(Contact contact);
}
