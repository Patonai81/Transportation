package hu.webuni.transportation.exception;

import hu.webuni.transportation.exception.base.ErrorProcessor;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
public class AddressRelatedException extends ErrorProcessor {

    private String defaultMesssage = "Problem with address related fields: ";

    public AddressRelatedException(String defaultMesssage) {
        super(null);
        this.defaultMesssage = defaultMesssage;
    }

    public AddressRelatedException(List<ObjectError> errors) {
        super(errors);
    }

    @Override
    public String getLocalizedMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(defaultMesssage);
        if (errors != null) {
            errors.stream().forEach(error -> builder.append(error.getCodes()[1] + " "));
        }
        return builder.toString();
    }
}
