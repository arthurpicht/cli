package de.arthurpicht.cli.command;

public class CommandSpecException extends RuntimeException {

    public CommandSpecException() {
    }

    public CommandSpecException(String message) {
        super(message);
    }

    public CommandSpecException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandSpecException(Throwable cause) {
        super(cause);
    }

    public CommandSpecException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
