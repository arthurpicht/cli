package de.arthurpicht.cli.command.exceptions;

import de.arthurpicht.cli.common.CLISpecificationException;

public class CommandSpecException extends CLISpecificationException {

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
