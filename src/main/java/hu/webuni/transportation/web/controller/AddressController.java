package hu.webuni.transportation.web.controller;

import hu.webuni.transportation.dto.AddressDTO;
import hu.webuni.transportation.dto.validator.AddressValidator;
import hu.webuni.transportation.exception.AddressRelatedException;
import hu.webuni.transportation.mapper.AddressMapper;
import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/addresses")
@Component("addressController")
public class AddressController {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressValidator addressValidator;

    @PostMapping
    public AddressDTO createAddress(@RequestBody AddressDTO addressDTO, BindingResult bindingResult) {
        log.debug("Validating input"+addressDTO);

        addressValidator.validate(addressDTO,bindingResult);
        if (bindingResult.hasErrors()){
          throw new AddressRelatedException(bindingResult.getAllErrors());
        }
        log.debug("Validation successful");

        Address address = addressMapper.toAddress(addressDTO);
        log.debug("Address mapping successfull");
        address = addressService.createAddress(address);
        AddressDTO result = addressMapper.toAddressDTO(address);
        log.debug("Address backmapping was successfull");
        return result;
    }

}
