package hu.webuni.transportation.web.controller;

import hu.webuni.transportation.model.Address;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AddressUtils {

    @Transactional
    public  void copyAddress(Address source, Address destination){

        destination.setCity(source.getCity());
        destination.setCountryCode(source.getCountryCode());
        destination.setStreet(source.getStreet());
        destination.setLatitude(source.getLatitude());
        destination.setLongitude(source.getLongitude());
        destination.setPostalCode(source.getPostalCode());

    }

}
