package hu.webuni.transportation.dto.validator;

import hu.webuni.transportation.dto.AddressDTO;
import hu.webuni.transportation.dto.AddressSearchDTO;
import hu.webuni.transportation.exception.AddressSearchEmptyException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddressSearchDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        {
            return AddressSearchDTO.class.equals(clazz);
        }
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target == null) {
            throw new AddressSearchEmptyException("All search fields are empty");
        }

        AddressSearchDTO addressSearchDTO = (AddressSearchDTO) target;
        if (addressSearchDTO.getCity() == null && addressSearchDTO.getCountryCode() == null && addressSearchDTO.getPostalCode() == null && addressSearchDTO.getStreet() == null)
            throw new AddressSearchEmptyException("All search fields are empty");

    }
}
