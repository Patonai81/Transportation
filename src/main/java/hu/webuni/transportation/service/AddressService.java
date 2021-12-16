package hu.webuni.transportation.service;

import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;


    public Address createAddress(Address address){

        log.debug("Address creation with address: "+address);
        Address addressFromRepo = addressRepository.save(address);
        log.debug("Address has been successfully created");

        return addressFromRepo;
    }

}
