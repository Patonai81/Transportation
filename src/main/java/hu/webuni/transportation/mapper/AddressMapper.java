package hu.webuni.transportation.mapper;

import hu.webuni.transportation.dto.AddressDTO;
import hu.webuni.transportation.model.Address;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {


    ArrayList<AddressDTO> toAddressDTOList(List<Address> address);


    AddressDTO toAddressDTO(Address address);

    Address toAddress(AddressDTO addressDTO);

}
