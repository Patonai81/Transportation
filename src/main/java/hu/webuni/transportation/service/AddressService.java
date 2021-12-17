package hu.webuni.transportation.service;

import hu.webuni.transportation.exception.AddressCannotBeFoundByIdException;
import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.repository.AddressRepository;
import hu.webuni.transportation.web.controller.AddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressUtils addressUtils;

    public Address create(Address address) {
        log.debug("Address creation with address: " + address);
        Address addressFromRepo = addressRepository.save(address);
        log.debug("Address has been successfully created");
        return addressFromRepo;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address find(Long id) {
        Optional<Address> addressFromRepo = addressRepository.findById(id);
        if (addressFromRepo.isPresent()) {
            log.debug(" Address is found with given Id: " + id + " : " + addressFromRepo.get());
            return addressFromRepo.get();
        }
        throw new AddressCannotBeFoundByIdException("Address cannot be found for given id: " + id);
    }

    public void delete(Long id) {

        if (addressRepository.existsByIdEquals(id)){
            log.debug("Address with given id exists before delete : " + id);
            addressRepository.deleteById(id);
        } else {
            log.debug("Address was not found with given id: "+id);
        }
    }

    @Transactional
    public Address modify(Long id, Address address) {
        Address addressFromRepo = find(id);
        addressUtils.copyAddress(address,addressFromRepo);
        log.debug("Address entity with id: "+id+" has been successfully changed.");
        return addressFromRepo;
    }



}
