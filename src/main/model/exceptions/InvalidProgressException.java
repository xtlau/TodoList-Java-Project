package model.exceptions;

public class InvalidProgressException extends IllegalArgumentException{
    public InvalidProgressException(String s) {
        super(s);
    }
}
