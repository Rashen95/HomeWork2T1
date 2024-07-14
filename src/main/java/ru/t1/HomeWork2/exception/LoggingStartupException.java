package ru.t1.HomeWork2.exception;

public class LoggingStartupException extends RuntimeException {

    public LoggingStartupException() {
    }

    public LoggingStartupException(String message) {
        super(message);
    }
}
