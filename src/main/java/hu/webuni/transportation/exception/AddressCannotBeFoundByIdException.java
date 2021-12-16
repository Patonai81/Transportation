package hu.webuni.transportation.exception;

public class AddressCannotBeFoundByIdException extends AddressRelatedException {

    public AddressCannotBeFoundByIdException(String defaultMesssage) {
        super(defaultMesssage);
    }


}
