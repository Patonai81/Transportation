package hu.webuni.transportation.mapper;

import hu.webuni.transportation.dto.AddressDTO;
import hu.webuni.transportation.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

AddressDTO toAddressDTO(Address address);

Address toAddress(AddressDTO addressDTO);

}
