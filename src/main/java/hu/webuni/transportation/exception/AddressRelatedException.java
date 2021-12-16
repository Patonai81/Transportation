package hu.webuni.transportation.exception;

import hu.webuni.transportation.exception.base.ErrorProcessor;
import org.springframework.validation.ObjectError;

import java.util.List;

public class AddressRelatedException extends ErrorProcessor {

    public AddressRelatedException(List<ObjectError> errors) {
        super(errors);
    }

    @Override
    public String getLocalizedMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Problem with address related fields: ");
        if (errors != null){
            errors.stream().forEach( error -> builder.append(error.getCodes()[1]+" "));
        }
        return builder.toString();
    }
}
