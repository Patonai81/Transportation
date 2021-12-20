package hu.webuni.transportation.exception.base;

public class NotAppropriateRegistrationInput extends RuntimeException{
    public NotAppropriateRegistrationInput(String message) {
        super(message);
    }
}
