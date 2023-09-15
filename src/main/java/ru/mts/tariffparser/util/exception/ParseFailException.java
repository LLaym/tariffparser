package ru.mts.tariffparser.util.exception;

public class ParseFailException extends RuntimeException {
    public ParseFailException(String message) {
        super(message);
    }
}
