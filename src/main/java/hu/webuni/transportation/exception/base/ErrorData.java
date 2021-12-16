package hu.webuni.transportation.exception.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ErrorData {
    private String errorCode;
    @EqualsAndHashCode.Exclude
    private String message;

}
