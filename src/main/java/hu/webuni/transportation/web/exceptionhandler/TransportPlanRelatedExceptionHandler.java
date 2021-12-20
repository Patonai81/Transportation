package hu.webuni.transportation.web.exceptionhandler;

import hu.webuni.transportation.exception.RegisterDelayDataNotFoundException;
import hu.webuni.transportation.exception.RegisterDelayDataNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TransportPlanRelatedExceptionHandler {


    @ExceptionHandler(RegisterDelayDataNotFoundException.class)
    public ResponseEntity<String> handleEmptySearch(RegisterDelayDataNotFoundException e) {
        log.error(e.getLocalizedMessage(),e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
    }

    @ExceptionHandler(RegisterDelayDataNotValidException.class)
    public ResponseEntity<String> handleEmptySearch(RegisterDelayDataNotValidException e) {
        log.error(e.getLocalizedMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
    }

}
