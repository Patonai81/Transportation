package hu.webuni.transportation.web.controller;

import hu.webuni.transportation.dto.AddressDTO;
import hu.webuni.transportation.dto.AddressSearchDTO;
import hu.webuni.transportation.dto.validator.AddressSearchDTOValidator;
import hu.webuni.transportation.dto.validator.AddressValidator;
import hu.webuni.transportation.exception.AddressRelatedException;
import hu.webuni.transportation.exception.AddressSearchEmptyException;
import hu.webuni.transportation.exception.PathAndEntityIdDoesNOTMATCHException;
import hu.webuni.transportation.mapper.AddressMapper;
import hu.webuni.transportation.model.Address;
import hu.webuni.transportation.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    AddressSearchDTOValidator addressSearchDTOValidator;

    @PostMapping
    public AddressDTO createAddress(@RequestBody AddressDTO addressDTO, BindingResult bindingResult) {
        log.debug("Validating input" + addressDTO);

        addressValidator.validate(addressDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new AddressRelatedException(bindingResult.getAllErrors());
        }
        log.debug("Validation successful");

        Address address = addressMapper.toAddress(addressDTO);
        log.debug("Address mapping successfull");
        address = addressService.create(address);
        AddressDTO result = addressMapper.toAddressDTO(address);
        log.debug("Address backmapping was successfull");
        return result;
    }

    @GetMapping
    public ArrayList<AddressDTO> getAllAddresses() {
        log.debug("Getting list of all registered addressed");
        return addressMapper.toAddressDTOList(addressService.findAll());
    }

    @GetMapping("/{id}")
    public AddressDTO getAddress(@PathVariable("id") Long id) {
        log.debug("Trying to find address with given id: " + id);
        Address addressFromRepos = addressService.find(id);
        log.debug("mapping Address entity to Address DTO entity");
        AddressDTO result = addressMapper.toAddressDTO(addressFromRepos);
        log.debug("Mapping was successfull");
        return result;
    }

    @DeleteMapping("/{id}")
    public void removeAddress(@PathVariable("id") Long id) {
        log.debug("Trying to delete address with given id: " + id);
        addressService.delete(id);
        log.debug("Address has been deleted");
    }

    @PutMapping("/{id}")
    public AddressDTO adjustAddress(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO, BindingResult bindingResult) {
        log.debug("Trying to modify address with given id: " + id);
        if (null != addressDTO.getId() && id != addressDTO.getId()) {
            throw new PathAndEntityIdDoesNOTMATCHException("incoming ID does not match");
        }
        addressValidator.validate(addressDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new AddressRelatedException(bindingResult.getAllErrors());
        }
        Address address = addressMapper.toAddress(addressDTO);
        log.debug("Address mapping successfull");

        Address addressFromRepo = addressService.modify(id, address);
        log.debug("Address has been modified" + addressFromRepo);

        AddressDTO addressDTOResult = addressMapper.toAddressDTO(addressFromRepo);
        log.debug("Address has been successfully mapped back to response");

        return addressDTOResult;

    }

    @PostMapping("/search")
    public List<AddressDTO> searchAddress(HttpServletResponse httpServletResponse, @RequestBody Optional<AddressSearchDTO> addressSearchDTOOpttional,
                                          @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault(page = 0,size = Integer.MAX_VALUE) Pageable pageable, BindingResult bindingResult) {

        List<AddressDTO> addressDTOS = null;
        AddressSearchDTO addressSearchDTO= null;

        //ha null törzs jön eldobjuk
        if (addressSearchDTOOpttional.isEmpty()) {
            log.debug("Empty search criteria returning empty list");
            throw new AddressSearchEmptyException("Serch criteria is empty");
        }

        addressSearchDTO = addressSearchDTOOpttional.get();
        //ha nem null search objektum jön, de minden mezője null akkor is eldobjuk
        addressSearchDTOValidator.validate(addressSearchDTO,bindingResult);

        addressSearchDTO.setPageable(pageable);
        log.debug("Looking for address(Es): " + addressSearchDTO);
        log.debug("Page parameters: " + pageable);

        Page<Address> addressPage = addressService.search(addressSearchDTO);
        log.debug("Result of search: " + addressPage);
        addressDTOS = addressMapper.toAddressDTOList(addressPage.getContent());
        log.debug("Mapping has been successfull");

        httpServletResponse.addHeader("X-Total-Count",String.valueOf(addressPage.getTotalElements()));
        return addressDTOS;
    }


}
