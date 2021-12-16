package hu.webuni.transportation.dto.validator;


import hu.webuni.transportation.dto.AddressDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AddressDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            errors.reject("BODY_NULL");
            return;
        }
        AddressDTO addressDTO = (AddressDTO) target;
        if (addressDTO.getId() != null) {
            errors.reject("BODY_ID_NOT_NULL");
        }
        ValidationUtils.rejectIfEmpty(errors, "countryCode", "BODY_FIELD_NULL");
        ValidationUtils.rejectIfEmpty(errors, "city", "BODY_FIELD_NULL");
        ValidationUtils.rejectIfEmpty(errors, "street", "BODY_FIELD_NULL");
        ValidationUtils.rejectIfEmpty(errors, "postalCode", "BODY_FIELD_NULL");

    }


}
