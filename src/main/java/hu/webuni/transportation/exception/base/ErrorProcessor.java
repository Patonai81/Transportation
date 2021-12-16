package hu.webuni.transportation.exception.base;

import hu.webuni.transportation.config.ErrorConstants;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ErrorProcessor extends RuntimeException {

    public List<ObjectError> errors;

    public ErrorProcessor(List<ObjectError> errors) {
        this.errors = errors;

    }

    public  List<ErrorData> createErrorData() {
        List<ErrorData> result = new ArrayList<>();
        if (errors != null) {
            errors.stream().forEach(error -> {
                ErrorData errorData = new ErrorData();
                errorData.setErrorCode(error.getCodes()[1]);
                errorData.setMessage(ErrorConstants.getErrorMessage(error.getCode()));
                result.add(errorData);
            });

        }
        return result;
    }


}
