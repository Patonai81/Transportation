package hu.webuni.transportation.config;

import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InitDb {

    @Autowired
    AddressRepository addressRepository;


    public void createTestAddresses(){
        Address address1 = Address.builder("HU","Budapest","1143","XYZ","11").build();
        Address address2 = Address.builder("HR","Zagreb","5522","TTX","12").build();
        Address address3 = Address.builder("SK","Pozsony","2345","EJF","13").build();
        Address address4 = Address.builder("HU","Budapest","1163","NNH","14").build();
        addressRepository.deleteAll();
        addressRepository.saveAllAndFlush(Arrays.asList(address1,address2,address3,address4));
    }

}



