package hu.webuni.transportation.web.exceptionhandler;

import hu.webuni.transportation.exception.AddressCannotBeFoundByIdException;
import hu.webuni.transportation.exception.AddressSearchEmptyException;
import hu.webuni.transportation.exception.PathAndEntityIdDoesNOTMATCHException;
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


    @ExceptionHandler(AddressCannotBeFoundByIdException.class)
    public ResponseEntity<String> handleAddressNotFoundByIdException(AddressCannotBeFoundByIdException e) {
        log.error(e.getLocalizedMessage(),e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
    }


    @ExceptionHandler(PathAndEntityIdDoesNOTMATCHException.class)
    public ResponseEntity<String> handleEntityIdException(PathAndEntityIdDoesNOTMATCHException e) {
        log.error(e.getLocalizedMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
    }

    @ExceptionHandler(AddressSearchEmptyException.class)
    public ResponseEntity<String> handleEmptySearch(AddressSearchEmptyException e) {
        log.error(e.getLocalizedMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


}
