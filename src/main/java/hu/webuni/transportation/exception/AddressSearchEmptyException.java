package hu.webuni.transportation.exception;

public class AddressSearchEmptyException extends RuntimeException{

    public AddressSearchEmptyException(String message) {
        super(message);
    }
}
