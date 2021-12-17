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
        Address address1 = Address.builder("HU","Budapest","1143","Kamilla","11").build();
        Address address2 = Address.builder("HR","Zagreb","5522","Viola","12").build();
        Address address3 = Address.builder("SK","Pozsony","2345","EJF","13").build();
        Address address4 = Address.builder("HU","Budapest","1163","Rózsa","18").build();
        Address address5 = Address.builder("HU","Budapest","1163","Pipacs","22/A").build();
        Address address6 = Address.builder("HU","Budapest","1113","Hóvirág","145/D").build();
        Address address7 = Address.builder("RO","Bukarest","1163231","Hóvirág","145/D").build();
        Address address8 = Address.builder("HU","Bugyi","4847","Hóvirág","145/D").build();
        addressRepository.deleteAll();
        addressRepository.saveAllAndFlush(Arrays.asList(address1,address2,address3,address4,address5,address6,address7,address8));
    }

}



