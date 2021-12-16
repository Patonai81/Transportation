package hu.webuni.transportation.web.exceptionhandler;

import hu.webuni.transportation.exception.AddressRelatedException;
import hu.webuni.transportation.exception.base.ErrorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class AddressRelatedExceptionHandler {

    @ExceptionHandler(AddressRelatedException.class)
    public ResponseEntity<List<ErrorData>> handleAddressException(AddressRelatedException e) {
        log.error(e.getLocalizedMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.createErrorData());
    }

}
