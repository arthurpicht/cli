package de.arthurpicht.cli.common;

public class CLISpecificationException extends RuntimeException {

    public CLISpecificationException() {
    }

    public CLISpecificationException(String message) {
        super(message);
    }

    public CLISpecificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CLISpecificationException(Throwable cause) {
        super(cause);
    }

    public CLISpecificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
