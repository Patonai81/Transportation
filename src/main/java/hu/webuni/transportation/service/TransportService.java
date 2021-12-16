package hu.webuni.transportation.service;

import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class TransportService {

    @Autowired
    AddressRepository addressRepository;

    Address createAddress(Address address){
        String methodName = "createAddress";
        log.debug(methodName +"ENTRY");
        Address addressFromRepo = addressRepository.save(address);
        log.debug(methodName+" EXIT");
        return addressFromRepo;
    }

}
