package com.restfullapi.service;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import com.restfullapi.entity.Address;
import com.restfullapi.entity.Contact;
import com.restfullapi.entity.User;
import com.restfullapi.model.AddressResponse;
import com.restfullapi.model.CreateAddressRequest;
import com.restfullapi.model.UpdateAddressRequest;
import com.restfullapi.repository.AddressRepository;
import com.restfullapi.repository.ContactRepository;
import com.restfullapi.security.BCrypt;

@Service
public class AddressService {
    
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirtsByUserAndId(user,request.getContactId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
        
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setContry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        addressRepository.save(address);
        
        return toAddressResponse(address);

    }

    public AddressResponse toAddressResponse(Address address){
        return AddressResponse.builder()
        .id(address.getId())
        .street(address.getStreet())
        .city(address.getCity())
        .province(address.getProvince())
        .country(address.getContry())
        .postalCode(address.getPostalCode())
        .build();
    }

    @Transactional(readOnly = true)
    public AddressResponse get(User user, String contactId, String addressId){
        Contact contact = contactRepository.findFirtsByUserAndId(user,contactId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = addressRepository.findFirstByContactAndId(contact, addressId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        return toAddressResponse(address);
    }

    @Transactional
    public AddressResponse update(User user, UpdateAddressRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirtsByUserAndId(user,request.getContactId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = addressRepository.findFirstByContactAndId(contact, request.getAddressId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setContry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        addressRepository.save(address);
        
        return toAddressResponse(address);
    }

    @Transactional
    public void remove(User user, String contactId, String addressId){
        Contact contact = contactRepository.findFirtsByUserAndId(user,contactId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = addressRepository.findFirstByContactAndId(contact, addressId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        addressRepository.delete(address);
    }

    public List<AddressResponse> list(User user, String contactId){
        Contact contact = contactRepository.findFirtsByUserAndId(user,contactId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        List<Address> addresses = addressRepository.findAllByContact(contact);
        return addresses.stream().map(this::toAddressResponse).toList();
    }
}
